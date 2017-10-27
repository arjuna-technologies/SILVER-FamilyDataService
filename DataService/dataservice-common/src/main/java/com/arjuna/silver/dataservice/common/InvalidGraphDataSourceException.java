/*
 * Copyright (c) 2017, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.silver.dataservice.common;

public class InvalidGraphDataSourceException extends Exception
{
    private static final long serialVersionUID = 4783653816908443297L;

    public InvalidGraphDataSourceException(String reason, String id)
    {
        super(reason + ": name \"" + id + "\"");

        _reason = reason;
        _id     = id;
    }

    public String getReason()
    {
        return _reason;
    }

    public String getId()
    {
        return _id;
    }

    private String _reason;
    private String _id;
}