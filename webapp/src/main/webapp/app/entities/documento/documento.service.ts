import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDocumento } from 'app/shared/model/documento.model';

type EntityResponseType = HttpResponse<IDocumento>;
type EntityArrayResponseType = HttpResponse<IDocumento[]>;

@Injectable({ providedIn: 'root' })
export class DocumentoService {
  public resourceUrl = SERVER_API_URL + 'api/documentos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/documentos';

  constructor(protected http: HttpClient) {}

  create(documento: IDocumento, file: File): Observable<EntityResponseType> {
    const formData: FormData = new FormData();
    const copy = this.convertDateFromClient(documento);

    const documentoBlob: Blob = new Blob([JSON.stringify(copy)], { type: 'application/json' });
    formData.append('documento', documentoBlob);
    formData.append('arquivo', file);

    return this.http
      .post<IDocumento>(this.resourceUrl, formData, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(documento: IDocumento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(documento);
    return this.http
      .put<IDocumento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDocumento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findWithConteudo(id: number, paginaInicial: number, paginaFinal: number): Observable<Blob> {
    return this.http.get(`${this.resourceUrl}/${id}/${paginaInicial}/${paginaFinal}/$conteudo`, { responseType: 'blob' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDocumento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  findAll(): Observable<EntityArrayResponseType> {
    return this.http
      .get<IDocumento[]>(`${this.resourceUrl}/all`, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  listBatch(): Observable<any> {
    return this.http.get<any>(`${this.resourceUrl}/batch`, { observe: 'response' });
  }

  createBatch(documento: IDocumento): Observable<EntityResponseType> {
    return this.http
      .post<IDocumento>(`${this.resourceUrl}/batch`, documento, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDocumento[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(documento: IDocumento): IDocumento {
    const copy: IDocumento = Object.assign({}, documento, {
      dataPublicacao:
        documento.dataPublicacao != null && documento.dataPublicacao.isValid() ? documento.dataPublicacao.format(DATE_FORMAT) : null,
      createdDate: documento.createdDate != null && documento.createdDate.isValid() ? documento.createdDate.toJSON() : null,
      lastModifiedDate:
        documento.lastModifiedDate != null && documento.lastModifiedDate.isValid() ? documento.lastModifiedDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataPublicacao = res.body.dataPublicacao != null ? moment(res.body.dataPublicacao) : null;
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
      res.body.lastModifiedDate = res.body.lastModifiedDate != null ? moment(res.body.lastModifiedDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((documento: IDocumento) => {
        documento.dataPublicacao = documento.dataPublicacao != null ? moment(documento.dataPublicacao) : null;
        documento.createdDate = documento.createdDate != null ? moment(documento.createdDate) : null;
        documento.lastModifiedDate = documento.lastModifiedDate != null ? moment(documento.lastModifiedDate) : null;
      });
    }
    return res;
  }
}
