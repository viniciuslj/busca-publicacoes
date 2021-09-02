import { Moment } from 'moment';
import { IDocumento } from 'app/shared/model/documento.model';

export interface IDiretorio {
  id?: number;
  nome?: string;
  descricao?: string;
  restrito?: boolean;
  filtroData?: boolean;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  documentos?: IDocumento[];
}

export class Diretorio implements IDiretorio {
  constructor(
    public id?: number,
    public nome?: string,
    public descricao?: string,
    public restrito?: boolean,
    public filtroData?: boolean,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public documentos?: IDocumento[]
  ) {
    this.restrito = this.restrito || false;
    this.filtroData = this.filtroData || false;
  }
}
