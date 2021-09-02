import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface ILogBusca {
  id?: number;
  conteudoBuscado?: string;
  quantidadeEncontrada?: number;
  tempoMs?: number;
  erro?: boolean;
  erroMsg?: any;
  createdDate?: Moment;
  user?: IUser;
}

export class LogBusca implements ILogBusca {
  constructor(
    public id?: number,
    public conteudoBuscado?: string,
    public quantidadeEncontrada?: number,
    public tempoMs?: number,
    public erro?: boolean,
    public erroMsg?: any,
    public createdDate?: Moment,
    public user?: IUser
  ) {
    this.erro = this.erro || false;
  }
}
