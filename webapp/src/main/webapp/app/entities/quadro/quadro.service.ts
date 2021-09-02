import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuadro } from 'app/shared/model/quadro.model';

type EntityResponseType = HttpResponse<IQuadro>;
type EntityArrayResponseType = HttpResponse<IQuadro[]>;

@Injectable({ providedIn: 'root' })
export class QuadroService {
  public resourceUrl = SERVER_API_URL + 'api/quadros';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/quadros';

  constructor(protected http: HttpClient) {}

  create(quadro: IQuadro): Observable<EntityResponseType> {
    return this.http.post<IQuadro>(this.resourceUrl, quadro, { observe: 'response' });
  }

  update(quadro: IQuadro): Observable<EntityResponseType> {
    return this.http.put<IQuadro>(this.resourceUrl, quadro, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuadro>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuadro[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuadro[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
