import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPagina } from 'app/shared/model/pagina.model';

type EntityResponseType = HttpResponse<IPagina>;
type EntityArrayResponseType = HttpResponse<IPagina[]>;

@Injectable({ providedIn: 'root' })
export class PaginaService {
  public resourceUrl = SERVER_API_URL + 'api/paginas';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/paginas';

  constructor(protected http: HttpClient) {}

  create(pagina: IPagina): Observable<EntityResponseType> {
    return this.http.post<IPagina>(this.resourceUrl, pagina, { observe: 'response' });
  }

  update(pagina: IPagina): Observable<EntityResponseType> {
    return this.http.put<IPagina>(this.resourceUrl, pagina, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPagina>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPagina[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPagina[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
