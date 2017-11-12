package br.uniceub.saafa.service;

import br.uniceub.saafa.service.dto.DepartamentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Departamento.
 */
public interface DepartamentoService {

    /**
     * Save a departamento.
     *
     * @param departamentoDTO the entity to save
     * @return the persisted entity
     */
    DepartamentoDTO save(DepartamentoDTO departamentoDTO);

    /**
     *  Get all the departamentos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<DepartamentoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" departamento.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DepartamentoDTO findOne(Long id);

    /**
     *  Delete the "id" departamento.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
