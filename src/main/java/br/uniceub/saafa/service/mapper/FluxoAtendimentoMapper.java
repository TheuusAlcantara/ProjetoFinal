package br.uniceub.saafa.service.mapper;

import br.uniceub.saafa.domain.*;
import br.uniceub.saafa.service.dto.FluxoAtendimentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FluxoAtendimento and its DTO FluxoAtendimentoDTO.
 */
@Mapper(componentModel = "spring", uses = {AgendamentoMapper.class, UBSMapper.class, FuncionarioMapper.class})
public interface FluxoAtendimentoMapper extends EntityMapper<FluxoAtendimentoDTO, FluxoAtendimento> {

    @Mapping(source = "agendamento.id", target = "agendamentoId")
    @Mapping(source = "ubs.id", target = "ubsId")
    FluxoAtendimentoDTO toDto(FluxoAtendimento fluxoAtendimento); 

    @Mapping(source = "agendamentoId", target = "agendamento")
    @Mapping(source = "ubsId", target = "ubs")
    @Mapping(target = "fluxoAtendimentos", ignore = true)
    FluxoAtendimento toEntity(FluxoAtendimentoDTO fluxoAtendimentoDTO);

    default FluxoAtendimento fromId(Long id) {
        if (id == null) {
            return null;
        }
        FluxoAtendimento fluxoAtendimento = new FluxoAtendimento();
        fluxoAtendimento.setId(id);
        return fluxoAtendimento;
    }
}
