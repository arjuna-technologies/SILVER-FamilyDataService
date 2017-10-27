import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';

import { MatCardModule } from '@angular/material';
import { MatFormFieldModule } from '@angular/material';
import { MatSelectModule } from '@angular/material';
import { MatInputModule } from '@angular/material';

import { AppComponent } from './app.component';
import { DatasourcesConfigService } from './config/datasources-config.service';
import { QueryDefLoaderService } from './datasources/query-def-loader.service';

import 'hammerjs';

@NgModule
({
    declarations:
    [
        AppComponent
    ],
    imports:
    [
        BrowserModule,
        BrowserAnimationsModule,
        FlexLayoutModule,
        MatCardModule,
        MatFormFieldModule,
        MatSelectModule,
        MatInputModule
    ],
    providers:
    [
        DatasourcesConfigService,
        QueryDefLoaderService
    ],
    bootstrap:
    [
        AppComponent
    ]
})
export class AppModule
{
}
