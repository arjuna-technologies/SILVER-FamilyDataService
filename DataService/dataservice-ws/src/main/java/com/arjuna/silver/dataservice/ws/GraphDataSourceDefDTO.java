/*
 * Copyright (c) 2017, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.silver.dataservice.ws;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder
({
    "id",
    "name",
    "query"
})
public class GraphDataSourceDefDTO implements Serializable
{
    private static final long serialVersionUID = -5192124189737663165L;

    public GraphDataSourceDefDTO()
    {
        _id    = null;
        _name  = null;
        _query = null;;
    }

    public GraphDataSourceDefDTO(String id, String name, String query)
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

    public void setQuery(String query)
    {
        _query = query;
    }

    public String getQuery()
    {
        return _query;
    }

    @JsonProperty("id")
    private String _id;
    @JsonProperty("name")
    private String _name;
    @JsonProperty("query")
    private String _query;
}