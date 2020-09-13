import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Data } from './data.model';

@Injectable({
    providedIn: 'root'
})

export class DataService {

    url = '/covid';

    constructor(private _http: HttpClient) {}

    getData() {
        return this._http.get<Data[]>(this.url);
    }
}