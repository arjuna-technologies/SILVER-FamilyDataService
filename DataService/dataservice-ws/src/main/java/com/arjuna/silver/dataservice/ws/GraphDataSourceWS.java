/*
 * Copyright (c) 2017, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.silver.dataservice.ws;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;
import com.arjuna.silver.dataservice.common.InvalidGraphDataSourceException;
import com.arjuna.silver.dataservice.graph.GraphDataSource;

@Path("/data")
@Stateless
public class GraphDataSourceWS
{
    private static final Logger logger = Logger.getLogger(GraphDataSourceWS.class.getName());

    @GET
    @Path("/data/{datasourceid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData(@PathParam("datasourceid") String dataSourceId, @Context UriInfo uriInfo)
    {
        try
        {
            logger.log(Level.WARNING, "GraphDataSourceWS.getData: [" + dataSourceId + "]");

            MultivaluedMap<String, String> rawQueryParams = uriInfo.getQueryParameters();
            Map<String, Object>            queryParams    = new HashMap<String, Object>();
            for (String key: rawQueryParams.keySet())
            {
                List<String> values = rawQueryParams.get(key);

                if (values.size() == 1)
                    queryParams.put(key, values.get(0));
                else if (values.size() > 1)
                {
                    logger.log(Level.WARNING, "GraphDataSourceWS.getData: multiple values for \"" + key + "\"");
                    queryParams.put(key, values.get(0));
                }
                else
                    logger.log(Level.WARNING, "GraphDataSourceWS.getData: no value for \"" + key + "\"");
            }

            StreamingOutput stream = new StreamingOutput()
            {
                @Override
                public void write(OutputStream outputStream)
                    throws IOException, WebApplicationException
                {
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream));

                    try
                    {
                        _graphDataSource.processData(dataSourceId, queryParams, writer);
                    }
                    catch (InvalidGraphDataSourceException invalidGraphDataSourceException)
                    {
                        logger.log(Level.WARNING, "GraphDataSourceWS.getData: Problem: " + invalidGraphDataSourceException.getMessage());
                    }
                }
            };

            return Response.ok(stream).build();
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "GraphDataSourceWS.getData: Unexpected Problem", throwable);

            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @EJB(beanName="Neo4jGraphDataSource")
    private GraphDataSource _graphDataSource;
}
