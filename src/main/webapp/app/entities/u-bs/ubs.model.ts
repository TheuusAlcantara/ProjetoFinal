import { BaseEntity } from './../../shared';

export class UBS implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public horaAbertura?: string,
        public horaFechamento?: string,
        public enderecoId?: number,
        public departamentosId?: number,
    ) {
    }
}
