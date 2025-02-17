import { Status } from "./Status";

export class Tarefa{
  id?: number;
  titulo!: string;
  descricao!: string;
  status!: Status;

  constructor(
    titulo: string,
    descricao: string,
    status: Status
  ){
    this.titulo = titulo,
    this.descricao = descricao,
    this.status = status
  }
}

