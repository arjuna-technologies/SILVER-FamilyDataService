/*
 * Copyright (c) 2017, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.silver.dataservice.store;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Column;

@Entity
public class GraphDataSourceDefEntity implements Serializable
{
    private static final long serialVersionUID = -1862031046681255121L;

    public GraphDataSourceDefEntity()
    {
    }

    public GraphDataSourceDefEntity(String id, String name, String query)
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

    @Id
    @Column(name = "id")
    protected String _id;

    @Column(name = "name")
    protected String _name;

    @Lob
    @Column(name = "query")
    protected String _query;
}
