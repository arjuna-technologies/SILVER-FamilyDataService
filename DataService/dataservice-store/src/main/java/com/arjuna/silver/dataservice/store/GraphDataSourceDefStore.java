/*
 * Copyright (c) 2017, Arjuna Technologies Limited, Newcastle-upon-Tyne, England. All rights reserved.
 */

package com.arjuna.silver.dataservice.store;

import java.util.List;
import com.arjuna.silver.dataservice.common.GraphDataSourceDef;

public interface GraphDataSourceDefStore
{
    public List<String> getGraphDataSourceDefIds();

    public GraphDataSourceDef getGraphDataSourceDef(String id);

    public boolean setGraphDataSourceDef(String id, GraphDataSourceDef graphDataSourceDef);
}
