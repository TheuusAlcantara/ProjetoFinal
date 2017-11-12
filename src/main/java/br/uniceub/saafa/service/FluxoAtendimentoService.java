package br.uniceub.saafa.service;

import br.uniceub.saafa.service.dto.FluxoAtendimentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FluxoAtendimento.
 */
public interface FluxoAtendimentoService {

    /**
     * Save a fluxoAtendimento.
     *
     * @param fluxoAtendimentoDTO the entity to save
     * @return the persisted entity
     */
    FluxoAtendimentoDTO save(FluxoAtendimentoDTO fluxoAtendimentoDTO);

    /**
     *  Get all the fluxoAtendimentos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FluxoAtendimentoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" fluxoAtendimento.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FluxoAtendimentoDTO findOne(Long id);

    /**
     *  Delete the "id" fluxoAtendimento.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
