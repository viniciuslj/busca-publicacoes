import { ICargo } from 'app/shared/model/cargo.model';
import { IQuadro } from 'app/shared/model/quadro.model';

export interface IUser {
  id?: any;
  login?: string;
  nome?: string;
  rg?: number;
  numeroFuncional?: number;
  email?: string;
  celular?: string;
  quadro?: IQuadro;
  cargo?: ICargo;
  activated?: boolean;
  createdDate?: Date;
  lastModifiedDate?: Date;
  authorities?: any[];
}

export class User implements IUser {
  constructor(
    public id?: any,
    public login?: string,
    public nome?: string,
    public rg?: number,
    public numeroFuncional?: number,
    public email?: string,
    public celular?: string,
    public quadro?: IQuadro,
    public cargo?: ICargo,
    public activated?: boolean,
    public createdDate?: Date,
    public lastModifiedDate?: Date,
    public authorities?: any[]
  ) {
    this.id = id ? id : null;
    this.login = login ? login : null;
    this.nome = nome ? nome : null;
    this.rg = rg ? rg : null;
    this.numeroFuncional = numeroFuncional ? numeroFuncional : null;
    this.email = email ? email : null;
    this.celular = celular ? celular : null;
    this.quadro = quadro ? quadro : null;
    this.cargo = cargo ? cargo : null;
    this.activated = activated ? activated : false;
    this.createdDate = createdDate ? createdDate : null;
    this.lastModifiedDate = lastModifiedDate ? lastModifiedDate : null;
    this.authorities = authorities ? authorities : null;
  }
}
