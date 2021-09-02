import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDiretorio } from 'app/shared/model/diretorio.model';

type EntityResponseType = HttpResponse<IDiretorio>;
type EntityArrayResponseType = HttpResponse<IDiretorio[]>;

@Injectable({ providedIn: 'root' })
export class DiretorioService {
  public resourceUrl = SERVER_API_URL + 'api/diretorios';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/diretorios';

  constructor(protected http: HttpClient) {}

  create(diretorio: IDiretorio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(diretorio);
    return this.http
      .post<IDiretorio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(diretorio: IDiretorio): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(diretorio);
    return this.http
      .put<IDiretorio>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDiretorio>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDiretorio[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDiretorio[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(diretorio: IDiretorio): IDiretorio {
    const copy: IDiretorio = Object.assign({}, diretorio, {
      createdDate: diretorio.createdDate != null && diretorio.createdDate.isValid() ? diretorio.createdDate.toJSON() : null,
      lastModifiedDate:
        diretorio.lastModifiedDate != null && diretorio.lastModifiedDate.isValid() ? diretorio.lastModifiedDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
      res.body.lastModifiedDate = res.body.lastModifiedDate != null ? moment(res.body.lastModifiedDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((diretorio: IDiretorio) => {
        diretorio.createdDate = diretorio.createdDate != null ? moment(diretorio.createdDate) : null;
        diretorio.lastModifiedDate = diretorio.lastModifiedDate != null ? moment(diretorio.lastModifiedDate) : null;
      });
    }
    return res;
  }
}
