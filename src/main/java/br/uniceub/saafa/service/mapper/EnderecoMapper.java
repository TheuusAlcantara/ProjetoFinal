package br.uniceub.saafa.service.mapper;

import br.uniceub.saafa.domain.*;
import br.uniceub.saafa.service.dto.EnderecoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Endereco and its DTO EnderecoDTO.
 */
@Mapper(componentModel = "spring", uses = {CidadeMapper.class})
public interface EnderecoMapper extends EntityMapper<EnderecoDTO, Endereco> {

    @Mapping(source = "cidade.id", target = "cidadeId")
    EnderecoDTO toDto(Endereco endereco); 

    @Mapping(source = "cidadeId", target = "cidade")
    Endereco toEntity(EnderecoDTO enderecoDTO);

    default Endereco fromId(Long id) {
        if (id == null) {
            return null;
        }
        Endereco endereco = new Endereco();
        endereco.setId(id);
        return endereco;
    }
}
