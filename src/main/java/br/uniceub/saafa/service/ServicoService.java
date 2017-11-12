package br.uniceub.saafa.service;

import br.uniceub.saafa.service.dto.ServicoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Servico.
 */
public interface ServicoService {

    /**
     * Save a servico.
     *
     * @param servicoDTO the entity to save
     * @return the persisted entity
     */
    ServicoDTO save(ServicoDTO servicoDTO);

    /**
     *  Get all the servicos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ServicoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" servico.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ServicoDTO findOne(Long id);

    /**
     *  Delete the "id" servico.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
