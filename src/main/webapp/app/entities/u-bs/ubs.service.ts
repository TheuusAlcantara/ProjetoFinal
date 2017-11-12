import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { UBS } from './ubs.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class UBSService {

    private resourceUrl = SERVER_API_URL + 'api/u-bs';

    constructor(private http: Http) { }

    create(uBS: UBS): Observable<UBS> {
        const copy = this.convert(uBS);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(uBS: UBS): Observable<UBS> {
        const copy = this.convert(uBS);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<UBS> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to UBS.
     */
    private convertItemFromServer(json: any): UBS {
        const entity: UBS = Object.assign(new UBS(), json);
        return entity;
    }

    /**
     * Convert a UBS to a JSON which can be sent to the server.
     */
    private convert(uBS: UBS): UBS {
        const copy: UBS = Object.assign({}, uBS);
        return copy;
    }
}
