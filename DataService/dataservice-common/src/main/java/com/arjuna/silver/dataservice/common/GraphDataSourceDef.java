/*
 * Copyright (c) 2017, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.silver.dataservice.common;

import java.io.Serializable;

public class GraphDataSourceDef implements Serializable
{
    private static final long serialVersionUID = -8569587165869423606L;

    public GraphDataSourceDef()
    {
    }

    public GraphDataSourceDef(String id, String name, String query)
    {
        _id    = id;
        _name  = name;
        _query = query;
    }

    public String getId()
    {
        return _id;
    }

    public void setId(String id)
    {
        _id = id;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public String getQuery()
    {
        return _query;
    }

    public void setQuery(String query)
    {
        _query = query;
    }

    private String _id;
    private String _name;
    private String _query;
}
