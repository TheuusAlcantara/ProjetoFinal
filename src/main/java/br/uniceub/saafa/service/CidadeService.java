package br.uniceub.saafa.service;

import br.uniceub.saafa.service.dto.CidadeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Cidade.
 */
public interface CidadeService {

    /**
     * Save a cidade.
     *
     * @param cidadeDTO the entity to save
     * @return the persisted entity
     */
    CidadeDTO save(CidadeDTO cidadeDTO);

    /**
     *  Get all the cidades.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CidadeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" cidade.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CidadeDTO findOne(Long id);

    /**
     *  Delete the "id" cidade.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
