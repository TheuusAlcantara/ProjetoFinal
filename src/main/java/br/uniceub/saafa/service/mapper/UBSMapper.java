package br.uniceub.saafa.service.mapper;

import br.uniceub.saafa.domain.*;
import br.uniceub.saafa.service.dto.UBSDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UBS and its DTO UBSDTO.
 */
@Mapper(componentModel = "spring", uses = {EnderecoMapper.class, DepartamentoMapper.class})
public interface UBSMapper extends EntityMapper<UBSDTO, UBS> {

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "departamentos.id", target = "departamentosId")
    UBSDTO toDto(UBS uBS); 

    @Mapping(source = "enderecoId", target = "endereco")
    @Mapping(source = "departamentosId", target = "departamentos")
    UBS toEntity(UBSDTO uBSDTO);

    default UBS fromId(Long id) {
        if (id == null) {
            return null;
        }
        UBS uBS = new UBS();
        uBS.setId(id);
        return uBS;
    }
}
