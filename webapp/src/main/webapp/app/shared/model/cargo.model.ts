export interface ICargo {
  id?: number;
  nome?: string;
  nomeLongo?: string;
  hierarquia?: number;
}

export class Cargo implements ICargo {
  constructor(public id?: number, public nome?: string, public nomeLongo?: string, public hierarquia?: number) {}
}
