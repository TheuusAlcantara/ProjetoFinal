package br.uniceub.saafa.service.impl;

import br.uniceub.saafa.service.FluxoAtendimentoService;
import br.uniceub.saafa.domain.FluxoAtendimento;
import br.uniceub.saafa.repository.FluxoAtendimentoRepository;
import br.uniceub.saafa.service.dto.FluxoAtendimentoDTO;
import br.uniceub.saafa.service.mapper.FluxoAtendimentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing FluxoAtendimento.
 */
@Service
@Transactional
public class FluxoAtendimentoServiceImpl implements FluxoAtendimentoService{

    private final Logger log = LoggerFactory.getLogger(FluxoAtendimentoServiceImpl.class);

    private final FluxoAtendimentoRepository fluxoAtendimentoRepository;

    private final FluxoAtendimentoMapper fluxoAtendimentoMapper;

    public FluxoAtendimentoServiceImpl(FluxoAtendimentoRepository fluxoAtendimentoRepository, FluxoAtendimentoMapper fluxoAtendimentoMapper) {
        this.fluxoAtendimentoRepository = fluxoAtendimentoRepository;
        this.fluxoAtendimentoMapper = fluxoAtendimentoMapper;
    }

    /**
     * Save a fluxoAtendimento.
     *
     * @param fluxoAtendimentoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FluxoAtendimentoDTO save(FluxoAtendimentoDTO fluxoAtendimentoDTO) {
        log.debug("Request to save FluxoAtendimento : {}", fluxoAtendimentoDTO);
        FluxoAtendimento fluxoAtendimento = fluxoAtendimentoMapper.toEntity(fluxoAtendimentoDTO);
        fluxoAtendimento = fluxoAtendimentoRepository.save(fluxoAtendimento);
        return fluxoAtendimentoMapper.toDto(fluxoAtendimento);
    }

    /**
     *  Get all the fluxoAtendimentos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FluxoAtendimentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FluxoAtendimentos");
        return fluxoAtendimentoRepository.findAll(pageable)
            .map(fluxoAtendimentoMapper::toDto);
    }

    /**
     *  Get one fluxoAtendimento by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FluxoAtendimentoDTO findOne(Long id) {
        log.debug("Request to get FluxoAtendimento : {}", id);
        FluxoAtendimento fluxoAtendimento = fluxoAtendimentoRepository.findOneWithEagerRelationships(id);
        return fluxoAtendimentoMapper.toDto(fluxoAtendimento);
    }

    /**
     *  Delete the  fluxoAtendimento by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FluxoAtendimento : {}", id);
        fluxoAtendimentoRepository.delete(id);
    }
}
