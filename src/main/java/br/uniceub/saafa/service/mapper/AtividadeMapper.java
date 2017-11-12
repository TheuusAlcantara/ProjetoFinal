package br.uniceub.saafa.service.mapper;

import br.uniceub.saafa.domain.*;
import br.uniceub.saafa.service.dto.AtividadeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Atividade and its DTO AtividadeDTO.
 */
@Mapper(componentModel = "spring", uses = {FluxoAtendimentoMapper.class})
public interface AtividadeMapper extends EntityMapper<AtividadeDTO, Atividade> {

    @Mapping(source = "fluxoAtendimento.id", target = "fluxoAtendimentoId")
    AtividadeDTO toDto(Atividade atividade); 

    @Mapping(source = "fluxoAtendimentoId", target = "fluxoAtendimento")
    Atividade toEntity(AtividadeDTO atividadeDTO);

    default Atividade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Atividade atividade = new Atividade();
        atividade.setId(id);
        return atividade;
    }
}
