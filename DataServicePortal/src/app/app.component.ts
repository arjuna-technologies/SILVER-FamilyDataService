import { Component } from '@angular/core';

@Component
({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent
{
    public query:  string;
    public querys: string[];

    public constructor()
    {
        this.query  = 'Test1';
        this.querys = [ 'Test1', 'Test2', 'Test3', 'Test4' ];
    }
}
