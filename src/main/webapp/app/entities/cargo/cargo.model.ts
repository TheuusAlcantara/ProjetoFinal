import { BaseEntity } from './../../shared';

export class Cargo implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public descricao?: string,
    ) {
    }
}
