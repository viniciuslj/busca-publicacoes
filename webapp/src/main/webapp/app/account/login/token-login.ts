export class TokenUsuario {
  id: number;
  login: string;
  nome: string;
  rg: number;
  numeroFuncional: number;
  activated: boolean;
  image: boolean;
  situacaoId: number;
  situacao: string;
  quadroId: number;
  quadro: string;
  cargoId: number;
  cargo: string;
  unidadeId: number;
  unidade: string;
  unidadeLeftId: number;
  unidadeRightId: number;
  setorFuncaoId: number;
  setorFuncao: string;
  setorId: number;
  setor: string;
  setorLeftId: number;
  setorRightId: number;
  usuarioActiveDirectory: string;
  email: string;
  telefone: string;
  celular: string;
}

export class TokenLogin {
  sub: string;
  geoLatitude: number;
  geoLongitude: number;
  usuario: TokenUsuario;
  exp: number;
}
