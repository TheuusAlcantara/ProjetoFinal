import { BaseEntity } from './../../shared';

export const enum DiaSemana {
    'DOMINGO',
    'SEGUNDA',
    'TERCA',
    'QUARTA',
    'QUINTA',
    'SEXTA',
    'SABADO'
}

export class Expediente implements BaseEntity {
    constructor(
        public id?: number,
        public horarioEntrada?: string,
        public horarioSaida?: string,
        public diaSemana?: DiaSemana,
    ) {
    }
}
