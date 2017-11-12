package br.uniceub.saafa.service;

import br.uniceub.saafa.service.dto.CargoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Cargo.
 */
public interface CargoService {

    /**
     * Save a cargo.
     *
     * @param cargoDTO the entity to save
     * @return the persisted entity
     */
    CargoDTO save(CargoDTO cargoDTO);

    /**
     *  Get all the cargos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CargoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" cargo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CargoDTO findOne(Long id);

    /**
     *  Delete the "id" cargo.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
