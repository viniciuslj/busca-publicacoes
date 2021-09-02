import { Moment } from 'moment';
import { IDiretorio } from 'app/shared/model/diretorio.model';

export interface IDocumento {
  id?: number;
  nome?: string;
  descricao?: string;
  path?: string;
  quantidadePaginas?: number;
  dataPublicacao?: Moment;
  processado?: boolean;
  erroProcessamento?: any;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  diretorio?: IDiretorio;
}

export class Documento implements IDocumento {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public path?: string,
    public quantidadePaginas?: number,
    public dataPublicacao?: Moment,
    public processado?: boolean,
    public erroProcessamento?: any,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public diretorio?: IDiretorio
  ) {
    this.processado = this.processado || false;
  }
}
