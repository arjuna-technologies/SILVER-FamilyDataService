//
// Copyright (c) 2017, Arjuna Technologies Limited, Newcastle upon Tyne, England,
//                     Open Lab, Newcastle University, Newcastle upon Tyne, England,
//                     Institute of Health and Society, Newcastle University, Newcastle upon Tyne, England.
//                     All rights reserved.
//

import { IOObject } from './io-object';

export class QueryDef implements IOObject
{
    public id:    string;
    public name:  string;
    public query: string;

    public fromObject(object: any): boolean
    {
        this.id    = object.id;
        this.name  = object.name;
        this.query = object.query;

        return true;
    }

    public toObject(): any
    {
        const queryDefObject: any = { };

        queryDefObject.id    = this.id;
        queryDefObject.name  = this.name;
        queryDefObject.query = this.query;

        return queryDefObject;
    }
}
