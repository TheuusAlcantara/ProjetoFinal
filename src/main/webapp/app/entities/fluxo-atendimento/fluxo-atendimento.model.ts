import { BaseEntity } from './../../shared';

export class FluxoAtendimento implements BaseEntity {
    constructor(
        public id?: number,
        public nomePaciente?: string,
        public razaoVisita?: string,
        public horaChegada?: string,
        public agendamentoId?: number,
        public ubsId?: number,
        public fluxoAtendimentos?: BaseEntity[],
        public funcionarios?: BaseEntity[],
    ) {
    }
}
