//
// Copyright (c) 2017, Arjuna Technologies Limited, Newcastle upon Tyne, England,
//                     Open Lab, Newcastle University, Newcastle upon Tyne, England,
//                     Institute of Health and Society, Newcastle University, Newcastle upon Tyne, England.
//                     All rights reserved.
//

import { IOObject } from './io-object';

export class ConsentTypeDef implements IOObject
{
    public id:          string;
    public name:        string;
    public rendererIds: string[];

    public fromObject(object: any): boolean
    {
        this.id          = object.id;
        this.name        = object.name;
        this.rendererIds = object.renderer_ids;

        return true;
    }

    public toObject(): any
    {
        const consentTypeDefObject: any = { };

        consentTypeDefObject.id           = this.id;
        consentTypeDefObject.name         = this.name;
        consentTypeDefObject.renderer_ids = this.rendererIds;

        return consentTypeDefObject;
    }
}
