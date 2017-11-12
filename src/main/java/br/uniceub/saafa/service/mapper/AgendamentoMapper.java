package br.uniceub.saafa.service.mapper;

import br.uniceub.saafa.domain.*;
import br.uniceub.saafa.service.dto.AgendamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Agendamento and its DTO AgendamentoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgendamentoMapper extends EntityMapper<AgendamentoDTO, Agendamento> {

    

    

    default Agendamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        Agendamento agendamento = new Agendamento();
        agendamento.setId(id);
        return agendamento;
    }
}
