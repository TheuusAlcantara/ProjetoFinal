import { BaseEntity } from './../../shared';

export class Atividade implements BaseEntity {
    constructor(
        public id?: number,
        public descricao?: string,
        public horaInicio?: string,
        public horaFim?: string,
        public fluxoAtendimentoId?: number,
    ) {
    }
}
