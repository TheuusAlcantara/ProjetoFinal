package br.uniceub.saafa.service.mapper;

import br.uniceub.saafa.domain.*;
import br.uniceub.saafa.service.dto.CargoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cargo and its DTO CargoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CargoMapper extends EntityMapper<CargoDTO, Cargo> {

    

    

    default Cargo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cargo cargo = new Cargo();
        cargo.setId(id);
        return cargo;
    }
}
