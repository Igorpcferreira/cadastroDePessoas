import {Contato} from "../contato/contato.model";
import {Endereco} from "../Endereco/endereco.model";

export interface Pessoa {
  id: number;
  nome: string;
  contato: Contato;
  endereco: Endereco;
}
