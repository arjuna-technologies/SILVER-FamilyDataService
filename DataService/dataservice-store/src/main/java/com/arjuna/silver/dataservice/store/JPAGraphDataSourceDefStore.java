/*
 * Copyright (c) 2017, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.silver.dataservice.store;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.arjuna.silver.dataservice.common.GraphDataSourceDef;

@Stateless(name="JPAGraphDataSourceDefStore")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class JPAGraphDataSourceDefStore implements GraphDataSourceDefStore
{
    private static final Logger logger = Logger.getLogger(JPAGraphDataSourceDefStore.class.getName());

    @Override
    public List<String> getGraphDataSourceDefIds()
    {
        logger.log(Level.FINE, "JPAGraphDataSourceDefStore.getGraphDataSourceDefIds");

        try
        {
            TypedQuery<GraphDataSourceDefEntity> query = _entityManager.createQuery("SELECT dsd FROM GraphDataSourceDefEntity AS dsd", GraphDataSourceDefEntity.class);

            List<String> ids = new LinkedList<String>();
            for (GraphDataSourceDefEntity dataSourceDef: query.getResultList())
                ids.add(dataSourceDef.getId());

            return ids;
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            return null;
        }
    }

    @Override
    public GraphDataSourceDef getGraphDataSourceDef(String id)
    {
        logger.log(Level.FINE, "JPAGraphDataSourceDefStore.getGraphDataSourceDef: \"" + id + "\"");

        try
        {
            GraphDataSourceDefEntity graphDataSourceDefEntity = _entityManager.find(GraphDataSourceDefEntity.class, id);

            if (graphDataSourceDefEntity != null)
            {
                GraphDataSourceDef graphDataSourceDef = new GraphDataSourceDef();

                graphDataSourceDef.setId(graphDataSourceDefEntity.getId());
                graphDataSourceDef.setName(graphDataSourceDefEntity.getName());
                graphDataSourceDef.setQuery(graphDataSourceDefEntity.getQuery());

                return graphDataSourceDef;
            }
            else
                return null;
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean setGraphDataSourceDef(String id, GraphDataSourceDef graphDataSourceDef)
    {
        logger.log(Level.FINE, "JPAGraphDataSourceDefStore.setDataSourceDef: \"" + id + "\"");

        try
        {
            GraphDataSourceDefEntity graphDataSourceDefEntity = _entityManager.find(GraphDataSourceDefEntity.class, id);

            if (graphDataSourceDefEntity != null)
            {
                graphDataSourceDefEntity.setId(graphDataSourceDef.getId());
                graphDataSourceDefEntity.setName(graphDataSourceDef.getName());
                graphDataSourceDefEntity.setQuery(graphDataSourceDef.getQuery());
                _entityManager.merge(graphDataSourceDefEntity);

                return true;
            }
            else
            {
                GraphDataSourceDefEntity newGraphDataSourceDefEntity = new GraphDataSourceDefEntity();

                newGraphDataSourceDefEntity.setId(graphDataSourceDef.getId());
                newGraphDataSourceDefEntity.setName(graphDataSourceDef.getName());
                newGraphDataSourceDefEntity.setQuery(graphDataSourceDef.getQuery());
                _entityManager.persist(newGraphDataSourceDefEntity);

                return true;
            }
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            return false;
        }
    }

    @PersistenceContext(unitName="SILVER")
    private EntityManager _entityManager;
}
