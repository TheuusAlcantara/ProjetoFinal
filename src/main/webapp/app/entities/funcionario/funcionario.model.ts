import { BaseEntity } from './../../shared';

export class Funcionario implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public cpf?: string,
        public enderecoId?: number,
        public cargoId?: number,
        public departamentos?: BaseEntity[],
        public expedientesId?: number,
        public consultas?: BaseEntity[],
    ) {
    }
}
