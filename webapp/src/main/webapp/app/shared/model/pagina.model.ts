import { IDocumento } from 'app/shared/model/documento.model';

export interface IPagina {
  id?: number;
  conteudo?: any;
  numero?: number;
  documento?: IDocumento;
}

export class Pagina implements IPagina {
  constructor(public id?: number, public conteudo?: any, public numero?: number, public documento?: IDocumento) {}
}
