package br.uniceub.saafa.service.mapper;

import br.uniceub.saafa.domain.*;
import br.uniceub.saafa.service.dto.ServicoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Servico and its DTO ServicoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServicoMapper extends EntityMapper<ServicoDTO, Servico> {

    

    

    default Servico fromId(Long id) {
        if (id == null) {
            return null;
        }
        Servico servico = new Servico();
        servico.setId(id);
        return servico;
    }
}
