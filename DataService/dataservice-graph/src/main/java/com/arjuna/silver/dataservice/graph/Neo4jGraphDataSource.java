/*
 * Copyright (c) 2017, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.silver.dataservice.graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Relationship;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.exceptions.ClientException;
import org.neo4j.driver.v1.util.Pair;
import com.arjuna.silver.dataservice.common.GraphDataSourceDef;
import com.arjuna.silver.dataservice.common.InvalidGraphDataSourceException;
import com.arjuna.silver.dataservice.store.GraphDataSourceDefStore;

@Stateless(name="Neo4jGraphDataSource")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class Neo4jGraphDataSource implements GraphDataSource
{
    private static final Logger logger = Logger.getLogger(Neo4jGraphDataSource.class.getName());

    public Neo4jGraphDataSource()
    {
        logger.log(Level.FINE, "Neo4jGraphDataSource");

        try
        {
            Properties properties = new Properties();

            FileInputStream neo4jConfigFileInputStream = new FileInputStream(System.getProperty("jboss.server.config.dir") + File.separator + "neo4j.conf");
            properties.load(neo4jConfigFileInputStream);
            neo4jConfigFileInputStream.close();

            _neo4jUsername   = properties.getProperty("username");
            _neo4jPassword   = properties.getProperty("password");
            _neo4jHostName   = properties.getProperty("hostname");
            _neo4jPortNumber = properties.getProperty("portnumber");
        }
        catch (Throwable throwable)
        {
            logger.log(Level.WARNING, "Failed to load neo4j properties", throwable);
        }
    }

    @Override
    public void processData(String dataSourceId, Map<String, Object> queryParams, PrintWriter writer)
        throws InvalidGraphDataSourceException
    {
        logger.log(Level.FINE, "Neo4jGraphDataSource.processData: " + dataSourceId + " - " + queryParams);

        try
        {
            GraphDataSourceDef graphDataSourceDef = _graphDataSourceDefStore.getGraphDataSourceDef(dataSourceId);

            if (graphDataSourceDef != null)
            {
                Driver  driver  = GraphDatabase.driver("bolt://" + _neo4jHostName + ":" + _neo4jPortNumber, AuthTokens.basic(_neo4jUsername, _neo4jPassword));
                Session session = driver.session();

                StatementResult result = session.run(graphDataSourceDef.getQuery(), queryParams);

                boolean firstRecord = true;
                writer.print('[');
                while (result.hasNext())
                {
                    if (firstRecord)
                        firstRecord = false;
                    else
                        writer.print(',');

                    Record record = result.next();

                    boolean pairRecord = true;
                    writer.print('{');
                    for (Pair<String, Value> pair: record.fields())
                    {
                        if (pairRecord)
                            pairRecord = false;
                        else
                            writer.print(',');

                        writeKey(pair.key(), session, writer);
                        writer.print(":");
                        writeValue(pair.value(), session, writer);
                    }
                    writer.print('}');
                }
                writer.println(']');
                writer.flush();

                session.close();
                driver.close();
            }
            else
            {
                writer.flush();

                throw new InvalidGraphDataSourceException("Unable to find DataSource", dataSourceId);
            }
        }
        catch (InvalidGraphDataSourceException invalidGraphDataSourceException)
        {
            writer.flush();

            throw invalidGraphDataSourceException;
        }
        catch (ClientException clientException)
        {
            writer.flush();

            throw new InvalidGraphDataSourceException("Neo4j problems: " + clientException.getMessage(), dataSourceId);
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();

            throw throwable;
        }
    }

    private void writeKey(String key, Session session, PrintWriter writer)
    {
        writer.print('"');
        writer.print(key);
        writer.print('"');
    }

    private void writeValue(Value value, Session session, PrintWriter writer)
    {
        if (value.hasType(session.typeSystem().STRING()))
        {
            writer.print('"');
            writer.print(value.asString());
            writer.print('"');
        }
        else if (value.hasType(session.typeSystem().NODE()))
        {
            Node node = value.asNode();

            Map<String, Object> map = node.asMap();

            writer.print("{\"_id\":\"");
            writer.print(node.id());
            writer.print("\",\"_type\":\"");
            writer.print(value.type().name());
            writer.print('"');
            for (Map.Entry<String, Object> entity: map.entrySet())
            {
                writer.print(",\"");
                writer.print(entity.getKey());
                writer.print("\":\"");
                writer.print(entity.getValue().toString());
                writer.print('"');
            }
            writer.print('}');
        }
        else if (value.hasType(session.typeSystem().NULL()))
            writer.print("null");
        else if (value.hasType(session.typeSystem().MAP()))
        {
            Map<String, Object> map = value.asMap();

            writer.print("{\"_type\":\"");
            writer.print(value.type().name());
            writer.print('"');
            for (Map.Entry<String, Object> entity: map.entrySet())
            {
                writer.print(",\"");
                writer.print(entity.getKey());
                writer.print("\":\"");
                writer.print(entity.getValue().toString());
                writer.print('"');
            }
            writer.print('}');
        }
        else if (value.hasType(session.typeSystem().RELATIONSHIP()))
        {
            Relationship relationship = value.asRelationship();

            long startNodeId = relationship.startNodeId();
            long endNodeId   = relationship.endNodeId();

            Value startNodeValue = null;
            Value endNodeValue   = null;

            writer.print("{{\"start\":\"");
            writer.print(startNodeId);
            writer.print("\"},{\"end\":\"");
            writer.print(endNodeId);
            writer.print("\"}");
            writer.print("{\"_type\":\"");
            writer.print(relationship.type());
            writer.print("\"}}");
        }
        else
            writer.print("\"*** Unknown Type " + value.type().name() + " ***\"");
    }

    private String _neo4jUsername;
    private String _neo4jPassword;
    private String _neo4jHostName;
    private String _neo4jPortNumber;

    @EJB(beanName="JPAGraphDataSourceDefStore")
    private GraphDataSourceDefStore _graphDataSourceDefStore;
}
