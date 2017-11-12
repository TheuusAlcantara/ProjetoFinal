package br.uniceub.saafa.service.impl;

import br.uniceub.saafa.service.AtividadeService;
import br.uniceub.saafa.domain.Atividade;
import br.uniceub.saafa.repository.AtividadeRepository;
import br.uniceub.saafa.service.dto.AtividadeDTO;
import br.uniceub.saafa.service.mapper.AtividadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Atividade.
 */
@Service
@Transactional
public class AtividadeServiceImpl implements AtividadeService{

    private final Logger log = LoggerFactory.getLogger(AtividadeServiceImpl.class);

    private final AtividadeRepository atividadeRepository;

    private final AtividadeMapper atividadeMapper;

    public AtividadeServiceImpl(AtividadeRepository atividadeRepository, AtividadeMapper atividadeMapper) {
        this.atividadeRepository = atividadeRepository;
        this.atividadeMapper = atividadeMapper;
    }

    /**
     * Save a atividade.
     *
     * @param atividadeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AtividadeDTO save(AtividadeDTO atividadeDTO) {
        log.debug("Request to save Atividade : {}", atividadeDTO);
        Atividade atividade = atividadeMapper.toEntity(atividadeDTO);
        atividade = atividadeRepository.save(atividade);
        return atividadeMapper.toDto(atividade);
    }

    /**
     *  Get all the atividades.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AtividadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Atividades");
        return atividadeRepository.findAll(pageable)
            .map(atividadeMapper::toDto);
    }

    /**
     *  Get one atividade by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AtividadeDTO findOne(Long id) {
        log.debug("Request to get Atividade : {}", id);
        Atividade atividade = atividadeRepository.findOne(id);
        return atividadeMapper.toDto(atividade);
    }

    /**
     *  Delete the  atividade by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Atividade : {}", id);
        atividadeRepository.delete(id);
    }
}
