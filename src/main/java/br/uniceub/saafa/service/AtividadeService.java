package br.uniceub.saafa.service;

import br.uniceub.saafa.service.dto.AtividadeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Atividade.
 */
public interface AtividadeService {

    /**
     * Save a atividade.
     *
     * @param atividadeDTO the entity to save
     * @return the persisted entity
     */
    AtividadeDTO save(AtividadeDTO atividadeDTO);

    /**
     *  Get all the atividades.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AtividadeDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" atividade.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AtividadeDTO findOne(Long id);

    /**
     *  Delete the "id" atividade.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
