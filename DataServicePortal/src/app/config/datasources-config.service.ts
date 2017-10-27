import { Injectable } from '@angular/core';

import { environment } from '../../environments/environment';

@Injectable()
export class DatasourcesConfigService
{
    private dataServiceProtocol: string;
    private dataServiceHostPort: string;

    public listQueryDefLoaderBaseURL: string;
    public getQueryDefLoaderBaseURL: string;
    public setQueryDefLoaderBaseURL: string;
    public deleteQueryDefLoaderBaseURL: string;

    constructor()
    {
        this.dataServiceProtocol = 'http://';

        this.dataServiceHostPort = 'hackday.silver.arjuna.com';

        this.listQueryDefLoaderBaseURL   = this.dataServiceProtocol + this.dataServiceHostPort + '/control/querys';
        this.getQueryDefLoaderBaseURL    = this.dataServiceProtocol + this.dataServiceHostPort + '/control/query';
        this.setQueryDefLoaderBaseURL    = this.dataServiceProtocol + this.dataServiceHostPort + '/control/query';
        this.deleteQueryDefLoaderBaseURL = this.dataServiceProtocol + this.dataServiceHostPort + '/control/query';
    }
}
