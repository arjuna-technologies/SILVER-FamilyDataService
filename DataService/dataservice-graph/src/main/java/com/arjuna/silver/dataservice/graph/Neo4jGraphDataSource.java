/*
 * Copyright (c) 2017, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.silver.dataservice.graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    {
        logger.log(Level.FINE, "Neo4jGraphDataSource.processData");

        try
        {
            Driver  driver  = GraphDatabase.driver("bolt://" + _neo4jHostName + ":" + _neo4jPortNumber, AuthTokens.basic(_neo4jUsername, _neo4jPassword));
            Session session = driver.session();

            StatementResult result = session.run("MATCH (a:Person) WHERE a.name = {name} RETURN a.name AS name, a.title AS title", queryParams);

            while (result.hasNext())
            {
                Record record = result.next();
                writer.println(record.get("title").asString() + " " + record.get("name").asString());
            }
            writer.flush();

            session.close();
            driver.close();
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();

            throw throwable;
        }
    }

    private String _neo4jUsername;
    private String _neo4jPassword;
    private String _neo4jHostName;
    private String _neo4jPortNumber;
}
