//
// Copyright (c) 2017, Arjuna Technologies Limited, Newcastle upon Tyne, England,
//                     Open Lab, Newcastle University, Newcastle upon Tyne, England,
//                     Institute of Health and Society, Newcastle University, Newcastle upon Tyne, England.
//                     All rights reserved.
//

import { IOObject } from './io-object';

export class QuerySummaryDef implements IOObject
{
    public id:    string;
    public name:  string;

    public fromObject(object: any): boolean
    {
        this.id    = object.id;
        this.name  = object.name;

        return true;
    }

    public toObject(): any
    {
        const querySummaryDefObject: any = { };

        querySummaryDefObject.id    = this.id;
        querySummaryDefObject.name  = this.name;

        return querySummaryDefObject;
    }
}
