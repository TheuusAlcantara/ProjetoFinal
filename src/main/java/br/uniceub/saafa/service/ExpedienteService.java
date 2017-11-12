package br.uniceub.saafa.service;

import br.uniceub.saafa.service.dto.ExpedienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Expediente.
 */
public interface ExpedienteService {

    /**
     * Save a expediente.
     *
     * @param expedienteDTO the entity to save
     * @return the persisted entity
     */
    ExpedienteDTO save(ExpedienteDTO expedienteDTO);

    /**
     *  Get all the expedientes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ExpedienteDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" expediente.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ExpedienteDTO findOne(Long id);

    /**
     *  Delete the "id" expediente.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
