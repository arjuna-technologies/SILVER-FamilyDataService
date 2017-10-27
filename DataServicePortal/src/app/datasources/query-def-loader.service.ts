//
// Copyright (c) 2017, Arjuna Technologies Limited, Newcastle upon Tyne, England,
//             Open Lab, Newcastle University, Newcastle upon Tyne, England,
//             Institute of Health and Society, Newcastle University, Newcastle upon Tyne, England.
//             All rights reserved.
//

import { Injectable } from '@angular/core';

import { Headers, Http, Response } from '@angular/http';
import 'rxjs/add/operator/toPromise';

import { QueryDef } from './query-def';
import { DatasourcesConfigService } from '../config/datasources-config.service';

@Injectable()
export class QueryDefLoaderService
{
    constructor(private http: Http, private datasourcesConfigService: DatasourcesConfigService)
    {
    }

    public getQueryDefIds(): Promise<String[]>
    {
        return this.http.get(this.datasourcesConfigService.listQueryDefLoaderBaseURL)
                   .toPromise()
                   .then((response) => Promise.resolve(this.getQueryDefIdsSuccessHandler(response)))
                   .catch((response) => Promise.resolve(this.getQueryDefIdsErrorHandler(response)));
    }

    public getQueryDef(id: string): Promise<QueryDef>
    {
        return this.http.get(this.datasourcesConfigService.getQueryDefLoaderBaseURL + '/' + id)
                   .toPromise()
                   .then((response) => Promise.resolve(this.getQueryDefSuccessHandler(response)))
                   .catch((response) => Promise.resolve(this.getQueryDefErrorHandler(response)));
    }

    public setQueryDef(id: string, queryDef: QueryDef): Promise<boolean>
    {
        return this.http.post(this.datasourcesConfigService.getQueryDefLoaderBaseURL + '/' + id, queryDef.toObject())
                   .toPromise()
                   .then((response) => Promise.resolve(this.setQueryDefSuccessHandler(response)))
                   .catch((response) => Promise.resolve(this.setQueryDefErrorHandler(response)));
    }

    private getQueryDefIdsSuccessHandler(response: Response): String[]
    {
        const queryDefs: QueryDef[] = [];

        for (const queryDefObject of response.json())
        {
             const queryDef = new QueryDef();

             queryDef.fromObject(queryDefObject);
             queryDefs.push(queryDef);
        }

        return queryDefs;
    }

    private getQueryDefIdsErrorHandler(error: Response | any): String[]
    {
        console.log('Error while loading Query Ids: ' + (error.message || error));

        return [];
    }

    private getQueryDefSuccessHandler(response: Response): QueryDef
    {
        const queryDef = new QueryDef();

        queryDef.fromObject(response.json());

        return queryDef;
    }

    private getQueryDefErrorHandler(error: Response | any): QueryDef
    {
        console.log('Error while loading Query: ' + (error.message || error));

        return null;
    }

    private setQueryDefSuccessHandler(response: Response): boolean
    {
        return true;
    }

    private setQueryDefErrorHandler(error: Response | any): boolean
    {
        console.log('Error while saving Query: ' + (error.message || error));

        return false;
    }
}
