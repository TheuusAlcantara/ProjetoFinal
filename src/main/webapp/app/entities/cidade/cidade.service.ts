import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Cidade } from './cidade.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CidadeService {

    private resourceUrl = SERVER_API_URL + 'api/cidades';

    constructor(private http: Http) { }

    create(cidade: Cidade): Observable<Cidade> {
        const copy = this.convert(cidade);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(cidade: Cidade): Observable<Cidade> {
        const copy = this.convert(cidade);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Cidade> {
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
     * Convert a returned JSON object to Cidade.
     */
    private convertItemFromServer(json: any): Cidade {
        const entity: Cidade = Object.assign(new Cidade(), json);
        return entity;
    }

    /**
     * Convert a Cidade to a JSON which can be sent to the server.
     */
    private convert(cidade: Cidade): Cidade {
        const copy: Cidade = Object.assign({}, cidade);
        return copy;
    }
}
