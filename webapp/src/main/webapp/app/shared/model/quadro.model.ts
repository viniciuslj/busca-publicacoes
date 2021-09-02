export interface IQuadro {
  id?: number;
  nome?: string;
  nomeLongo?: string;
}

export class Quadro implements IQuadro {
  constructor(public id?: number, public nome?: string, public nomeLongo?: string) {}
}
