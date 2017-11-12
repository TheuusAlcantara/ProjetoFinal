package br.uniceub.saafa.service.mapper;

import br.uniceub.saafa.domain.*;
import br.uniceub.saafa.service.dto.FuncionarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Funcionario and its DTO FuncionarioDTO.
 */
@Mapper(componentModel = "spring", uses = {EnderecoMapper.class, CargoMapper.class, ExpedienteMapper.class})
public interface FuncionarioMapper extends EntityMapper<FuncionarioDTO, Funcionario> {

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "cargo.id", target = "cargoId")
    @Mapping(source = "expedientes.id", target = "expedientesId")
    FuncionarioDTO toDto(Funcionario funcionario); 

    @Mapping(source = "enderecoId", target = "endereco")
    @Mapping(source = "cargoId", target = "cargo")
    @Mapping(target = "departamentos", ignore = true)
    @Mapping(source = "expedientesId", target = "expedientes")
    @Mapping(target = "consultas", ignore = true)
    Funcionario toEntity(FuncionarioDTO funcionarioDTO);

    default Funcionario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        return funcionario;
    }
}
