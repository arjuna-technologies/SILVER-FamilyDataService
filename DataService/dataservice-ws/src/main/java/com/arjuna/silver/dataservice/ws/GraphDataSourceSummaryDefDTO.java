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
    "name"
})
public class GraphDataSourceSummaryDefDTO implements Serializable
{
	private static final long serialVersionUID = 3944433638028814484L;

	public GraphDataSourceSummaryDefDTO()
    {
        _id    = null;
        _name  = null;
    }

    public GraphDataSourceSummaryDefDTO(String id, String name)
    {
        _id    = id;
        _name  = name;
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

    @JsonProperty("id")
    private String _id;
    @JsonProperty("name")
    private String _name;
}
