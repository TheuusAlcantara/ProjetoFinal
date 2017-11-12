import { BaseEntity } from './../../shared';

export class Departamento implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public descricao?: string,
        public servicosId?: number,
        public funcionarioId?: number,
    ) {
    }
}
