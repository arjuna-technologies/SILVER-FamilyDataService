/*
 * Copyright (c) 2017, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.silver.dataservice.ws;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.arjuna.silver.dataservice.common.GraphDataSourceDef;
import com.arjuna.silver.dataservice.store.GraphDataSourceDefStore;

@Path("/defs")
@Stateless
public class GraphDataSourceDefWS
{
    private static final Logger logger = Logger.getLogger(GraphDataSourceDefWS.class.getName());

    @GET
    @Path("/querys")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getGraphDataSourceDefIds()
    {
        try
        {
            logger.log(Level.FINE, "GraphDataSourceDefWS.getGraphDataSourceDefs");

            return _graphDataSourceDefStore.getGraphDataSourceDefIds();
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "GraphDataSourceDefWS.getGraphDataSourceDefs: Unable to metadata", throwable);

            return Collections.emptyList();
        }
    }

    @GET
    @Path("/query/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public GraphDataSourceDefDTO getGraphDataSourceDef(@PathParam("id") String id)
    {
        try
        {
            logger.log(Level.FINE, "GraphDataSourceDefDTO.getGraphDataSourceDef [" + id + "]");

            if (id == null)
            {
                logger.log(Level.WARNING, "GraphDataSourceDefDTO.getGraphDataSourceDef: Invalid parameters: id=[" + id + "]");
                return null;
            }

            GraphDataSourceDef graphDataSourceDef = _graphDataSourceDefStore.getGraphDataSourceDef(id);

            if (graphDataSourceDef != null)
            {
                GraphDataSourceDefDTO graphDataSourceDefDTO = new GraphDataSourceDefDTO();

                graphDataSourceDefDTO.setId(graphDataSourceDef.getId());
                graphDataSourceDefDTO.setName(graphDataSourceDef.getName());
                graphDataSourceDefDTO.setQuery(graphDataSourceDef.getQuery());

                return graphDataSourceDefDTO;
            }
            else
            {
                logger.log(Level.WARNING, "GraphDataSourceDefDTO.getGraphDataSourceDef: No graphDataSourceDef [" + id + "]");
                return null;
            }
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "GraphDataSourceDefDTO.getGraphDataSourceDef: Unexpected Problem", throwable);
            return null;
        }
    }

    @POST
    @Path("/query/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void postGraphDataSourceDef(@PathParam("id") String id, GraphDataSourceDefDTO graphDataSourceDefDTO)
    {
        logger.log(Level.FINE, "GraphDataSourceDefDTO.postGraphDataSourceDef [" + id + "]");

        try
        {
            if (id == null)
                logger.log(Level.WARNING, "GraphDataSourceDefDTO.postGraphDataSourceDef: Invalid parameters: id=[" + id + "]");

            if (graphDataSourceDefDTO != null)
            {
                GraphDataSourceDef graphDataSourceDef = new GraphDataSourceDef();

                graphDataSourceDef.setId(graphDataSourceDefDTO.getId());
                graphDataSourceDef.setName(graphDataSourceDefDTO.getName());
                graphDataSourceDef.setQuery(graphDataSourceDefDTO.getQuery());

                _graphDataSourceDefStore.setGraphDataSourceDef(id, graphDataSourceDef);
            }
            else
                logger.log(Level.WARNING, "GraphDataSourceDefDTO.postGraphDataSourceDef: Can't be access");
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "GraphDataSourceDefDTO.postGraphDataSourceDef: Unexpected problem", throwable);
        }
    }

    @EJB(beanName="JPAGraphDataSourceDefStore")
    private GraphDataSourceDefStore _graphDataSourceDefStore;
}
