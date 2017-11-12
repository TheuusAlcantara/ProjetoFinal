package br.uniceub.saafa.service.impl;

import br.uniceub.saafa.service.ExpedienteService;
import br.uniceub.saafa.domain.Expediente;
import br.uniceub.saafa.repository.ExpedienteRepository;
import br.uniceub.saafa.service.dto.ExpedienteDTO;
import br.uniceub.saafa.service.mapper.ExpedienteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Expediente.
 */
@Service
@Transactional
public class ExpedienteServiceImpl implements ExpedienteService{

    private final Logger log = LoggerFactory.getLogger(ExpedienteServiceImpl.class);

    private final ExpedienteRepository expedienteRepository;

    private final ExpedienteMapper expedienteMapper;

    public ExpedienteServiceImpl(ExpedienteRepository expedienteRepository, ExpedienteMapper expedienteMapper) {
        this.expedienteRepository = expedienteRepository;
        this.expedienteMapper = expedienteMapper;
    }

    /**
     * Save a expediente.
     *
     * @param expedienteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExpedienteDTO save(ExpedienteDTO expedienteDTO) {
        log.debug("Request to save Expediente : {}", expedienteDTO);
        Expediente expediente = expedienteMapper.toEntity(expedienteDTO);
        expediente = expedienteRepository.save(expediente);
        return expedienteMapper.toDto(expediente);
    }

    /**
     *  Get all the expedientes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExpedienteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Expedientes");
        return expedienteRepository.findAll(pageable)
            .map(expedienteMapper::toDto);
    }

    /**
     *  Get one expediente by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ExpedienteDTO findOne(Long id) {
        log.debug("Request to get Expediente : {}", id);
        Expediente expediente = expedienteRepository.findOne(id);
        return expedienteMapper.toDto(expediente);
    }

    /**
     *  Delete the  expediente by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Expediente : {}", id);
        expedienteRepository.delete(id);
    }
}
