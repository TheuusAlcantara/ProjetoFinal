import { BaseEntity } from './../../shared';

export class Endereco implements BaseEntity {
    constructor(
        public id?: number,
        public logradouro?: string,
        public numero?: string,
        public complemento?: string,
        public cidadeId?: number,
    ) {
    }
}
