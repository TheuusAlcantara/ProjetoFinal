package br.uniceub.saafa.service.mapper;

import br.uniceub.saafa.domain.*;
import br.uniceub.saafa.service.dto.CidadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cidade and its DTO CidadeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CidadeMapper extends EntityMapper<CidadeDTO, Cidade> {

    

    

    default Cidade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cidade cidade = new Cidade();
        cidade.setId(id);
        return cidade;
    }
}
