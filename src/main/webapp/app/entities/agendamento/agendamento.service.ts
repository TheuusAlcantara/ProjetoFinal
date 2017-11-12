import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Agendamento } from './agendamento.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AgendamentoService {

    private resourceUrl = SERVER_API_URL + 'api/agendamentos';

    constructor(private http: Http) { }

    create(agendamento: Agendamento): Observable<Agendamento> {
        const copy = this.convert(agendamento);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(agendamento: Agendamento): Observable<Agendamento> {
        const copy = this.convert(agendamento);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Agendamento> {
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
     * Convert a returned JSON object to Agendamento.
     */
    private convertItemFromServer(json: any): Agendamento {
        const entity: Agendamento = Object.assign(new Agendamento(), json);
        return entity;
    }

    /**
     * Convert a Agendamento to a JSON which can be sent to the server.
     */
    private convert(agendamento: Agendamento): Agendamento {
        const copy: Agendamento = Object.assign({}, agendamento);
        return copy;
    }
}
