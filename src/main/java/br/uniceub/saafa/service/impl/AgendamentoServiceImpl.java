package br.uniceub.saafa.service.impl;

import br.uniceub.saafa.service.AgendamentoService;
import br.uniceub.saafa.domain.Agendamento;
import br.uniceub.saafa.repository.AgendamentoRepository;
import br.uniceub.saafa.service.dto.AgendamentoDTO;
import br.uniceub.saafa.service.mapper.AgendamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Agendamento.
 */
@Service
@Transactional
public class AgendamentoServiceImpl implements AgendamentoService{

    private final Logger log = LoggerFactory.getLogger(AgendamentoServiceImpl.class);

    private final AgendamentoRepository agendamentoRepository;

    private final AgendamentoMapper agendamentoMapper;

    public AgendamentoServiceImpl(AgendamentoRepository agendamentoRepository, AgendamentoMapper agendamentoMapper) {
        this.agendamentoRepository = agendamentoRepository;
        this.agendamentoMapper = agendamentoMapper;
    }

    /**
     * Save a agendamento.
     *
     * @param agendamentoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AgendamentoDTO save(AgendamentoDTO agendamentoDTO) {
        log.debug("Request to save Agendamento : {}", agendamentoDTO);
        Agendamento agendamento = agendamentoMapper.toEntity(agendamentoDTO);
        agendamento = agendamentoRepository.save(agendamento);
        return agendamentoMapper.toDto(agendamento);
    }

    /**
     *  Get all the agendamentos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AgendamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Agendamentos");
        return agendamentoRepository.findAll(pageable)
            .map(agendamentoMapper::toDto);
    }

    /**
     *  Get one agendamento by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AgendamentoDTO findOne(Long id) {
        log.debug("Request to get Agendamento : {}", id);
        Agendamento agendamento = agendamentoRepository.findOne(id);
        return agendamentoMapper.toDto(agendamento);
    }

    /**
     *  Delete the  agendamento by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Agendamento : {}", id);
        agendamentoRepository.delete(id);
    }
}
