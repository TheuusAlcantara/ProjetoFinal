package br.uniceub.saafa.service.impl;

import br.uniceub.saafa.service.DepartamentoService;
import br.uniceub.saafa.domain.Departamento;
import br.uniceub.saafa.repository.DepartamentoRepository;
import br.uniceub.saafa.service.dto.DepartamentoDTO;
import br.uniceub.saafa.service.mapper.DepartamentoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Departamento.
 */
@Service
@Transactional
public class DepartamentoServiceImpl implements DepartamentoService{

    private final Logger log = LoggerFactory.getLogger(DepartamentoServiceImpl.class);

    private final DepartamentoRepository departamentoRepository;

    private final DepartamentoMapper departamentoMapper;

    public DepartamentoServiceImpl(DepartamentoRepository departamentoRepository, DepartamentoMapper departamentoMapper) {
        this.departamentoRepository = departamentoRepository;
        this.departamentoMapper = departamentoMapper;
    }

    /**
     * Save a departamento.
     *
     * @param departamentoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DepartamentoDTO save(DepartamentoDTO departamentoDTO) {
        log.debug("Request to save Departamento : {}", departamentoDTO);
        Departamento departamento = departamentoMapper.toEntity(departamentoDTO);
        departamento = departamentoRepository.save(departamento);
        return departamentoMapper.toDto(departamento);
    }

    /**
     *  Get all the departamentos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DepartamentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Departamentos");
        return departamentoRepository.findAll(pageable)
            .map(departamentoMapper::toDto);
    }

    /**
     *  Get one departamento by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DepartamentoDTO findOne(Long id) {
        log.debug("Request to get Departamento : {}", id);
        Departamento departamento = departamentoRepository.findOne(id);
        return departamentoMapper.toDto(departamento);
    }

    /**
     *  Delete the  departamento by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Departamento : {}", id);
        departamentoRepository.delete(id);
    }
}
