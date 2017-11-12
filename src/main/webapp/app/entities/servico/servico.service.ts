import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Servico } from './servico.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ServicoService {

    private resourceUrl = SERVER_API_URL + 'api/servicos';

    constructor(private http: Http) { }

    create(servico: Servico): Observable<Servico> {
        const copy = this.convert(servico);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(servico: Servico): Observable<Servico> {
        const copy = this.convert(servico);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Servico> {
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
     * Convert a returned JSON object to Servico.
     */
    private convertItemFromServer(json: any): Servico {
        const entity: Servico = Object.assign(new Servico(), json);
        return entity;
    }

    /**
     * Convert a Servico to a JSON which can be sent to the server.
     */
    private convert(servico: Servico): Servico {
        const copy: Servico = Object.assign({}, servico);
        return copy;
    }
}
