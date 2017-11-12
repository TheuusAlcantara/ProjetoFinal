package br.uniceub.saafa.service.mapper;

import br.uniceub.saafa.domain.*;
import br.uniceub.saafa.service.dto.DepartamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Departamento and its DTO DepartamentoDTO.
 */
@Mapper(componentModel = "spring", uses = {ServicoMapper.class, FuncionarioMapper.class})
public interface DepartamentoMapper extends EntityMapper<DepartamentoDTO, Departamento> {

    @Mapping(source = "servicos.id", target = "servicosId")
    @Mapping(source = "funcionario.id", target = "funcionarioId")
    DepartamentoDTO toDto(Departamento departamento); 

    @Mapping(source = "servicosId", target = "servicos")
    @Mapping(source = "funcionarioId", target = "funcionario")
    Departamento toEntity(DepartamentoDTO departamentoDTO);

    default Departamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Departamento departamento = new Departamento();
        departamento.setId(id);
        return departamento;
    }
}
