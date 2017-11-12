package br.uniceub.saafa.service;

import br.uniceub.saafa.service.dto.UBSDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing UBS.
 */
public interface UBSService {

    /**
     * Save a uBS.
     *
     * @param uBSDTO the entity to save
     * @return the persisted entity
     */
    UBSDTO save(UBSDTO uBSDTO);

    /**
     *  Get all the uBS.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UBSDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" uBS.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UBSDTO findOne(Long id);

    /**
     *  Delete the "id" uBS.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
