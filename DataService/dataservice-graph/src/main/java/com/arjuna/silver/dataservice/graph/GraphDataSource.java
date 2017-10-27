/*
 * Copyright (c) 2017, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.silver.dataservice.graph;

import java.io.PrintWriter;
import java.util.Map;
import com.arjuna.silver.dataservice.common.InvalidGraphDataSourceException;

public interface GraphDataSource
{
    public void processData(String dataSourceId, Map<String, Object> queryParams, PrintWriter writer)
        throws InvalidGraphDataSourceException;
}
