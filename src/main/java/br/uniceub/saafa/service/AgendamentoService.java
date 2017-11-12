package br.uniceub.saafa.service;

import br.uniceub.saafa.service.dto.AgendamentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Agendamento.
 */
public interface AgendamentoService {

    /**
     * Save a agendamento.
     *
     * @param agendamentoDTO the entity to save
     * @return the persisted entity
     */
    AgendamentoDTO save(AgendamentoDTO agendamentoDTO);

    /**
     *  Get all the agendamentos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AgendamentoDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" agendamento.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AgendamentoDTO findOne(Long id);

    /**
     *  Delete the "id" agendamento.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
