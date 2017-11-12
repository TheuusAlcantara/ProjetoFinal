package br.uniceub.saafa.service.mapper;

import br.uniceub.saafa.domain.*;
import br.uniceub.saafa.service.dto.ExpedienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Expediente and its DTO ExpedienteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExpedienteMapper extends EntityMapper<ExpedienteDTO, Expediente> {

    

    

    default Expediente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Expediente expediente = new Expediente();
        expediente.setId(id);
        return expediente;
    }
}
