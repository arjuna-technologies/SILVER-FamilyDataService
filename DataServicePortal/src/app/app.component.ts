import { Component } from '@angular/core';

import { QuerySummaryModel } from './model/query-summary-model';
import { QueryModel } from './model/query-model';

import { QueryDef } from './datasources/query-def';
import { QuerySummaryDef } from './datasources/query-summary-def';
import { QueryDefLoaderService } from './datasources/query-def-loader.service';

@Component
({
    selector:    'app-root',
    templateUrl: './app.component.html',
    styleUrls:   [ './app.component.scss' ]
})
export class AppComponent
{
    public querySummaries:  QuerySummaryModel[];
    public selectedQuery:   QueryModel;

    public constructor(private queryDefLoaderService: QueryDefLoaderService)
    {
        this.querySummaries  = [];
        this.selectedQuery   = new QueryModel();

        this.loadQuerySummaryDef();
    }

    private loadQuerySummaryDef(): void
    {
        this.querySummaries  = [];

        this.queryDefLoaderService.getQueryDefSummarys()
            .then((queryDefSummarys) => { this.processQueryDefSummarys(queryDefSummarys) })
            .catch(() => { this.processQueryDefSummarys([ ]) });
    }

    public doQueryChange(event: any): void
    {
        this.selectedQuery.id    = '';
        this.selectedQuery.name  = '';
        this.selectedQuery.query = '';

        this.queryDefLoaderService.getQueryDef(event.value)
            .then((queryDef) => { this.processQueryDef(queryDef) })
            .catch(() => { this.processQueryDef(null) });
    }

    public doNewQueryDef(): void
    {
        this.selectedQuery.id    = '';
        this.selectedQuery.name  = '';
        this.selectedQuery.query = '';
    }

    public doSaveQueryDef(): void
    {
        const queryDef: QueryDef = new QueryDef();
        queryDef.id    = this.selectedQuery.id;
        queryDef.name  = this.selectedQuery.name;
        queryDef.query = this.selectedQuery.query;

        this.queryDefLoaderService.setQueryDef(this.selectedQuery.id, queryDef)
            .then(() => { this.loadQuerySummaryDef() });
    }

    public doRemoveQueryDef(): void
    {
        const id = this.selectedQuery.id;

        this.selectedQuery.id    = '';
        this.selectedQuery.name  = '';
        this.selectedQuery.query = '';

        this.queryDefLoaderService.deleteQueryDef(id)
            .then(() => { this.loadQuerySummaryDef() });
    }

    private processQueryDefSummarys(querySummaryDefs: QuerySummaryDef[]): void
    {
        this.querySummaries = [];

        for (const querySummaryDef of querySummaryDefs)
        {
            const querySummaryModel = new QuerySummaryModel();

            querySummaryModel.id   = querySummaryDef.id;
            querySummaryModel.name = querySummaryDef.name;

            this.querySummaries.push(querySummaryModel);
        }
    }

    private processQueryDef(queryDef: QueryDef): void
    {
        if (queryDef !== null)
        {
            this.selectedQuery.id    = queryDef.id;
            this.selectedQuery.name  = queryDef.name;
            this.selectedQuery.query = queryDef.query;
        }
        else
        {
            this.selectedQuery.id    = '';
            this.selectedQuery.name  = '';
            this.selectedQuery.query = '';
        }
    }
}
