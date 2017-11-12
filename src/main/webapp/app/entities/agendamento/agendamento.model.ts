import { BaseEntity } from './../../shared';

export class Agendamento implements BaseEntity {
    constructor(
        public id?: number,
        public horario?: string,
    ) {
    }
}
