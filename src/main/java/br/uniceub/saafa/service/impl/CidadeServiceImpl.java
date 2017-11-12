package br.uniceub.saafa.service.impl;

import br.uniceub.saafa.service.CidadeService;
import br.uniceub.saafa.domain.Cidade;
import br.uniceub.saafa.repository.CidadeRepository;
import br.uniceub.saafa.service.dto.CidadeDTO;
import br.uniceub.saafa.service.mapper.CidadeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Cidade.
 */
@Service
@Transactional
public class CidadeServiceImpl implements CidadeService{

    private final Logger log = LoggerFactory.getLogger(CidadeServiceImpl.class);

    private final CidadeRepository cidadeRepository;

    private final CidadeMapper cidadeMapper;

    public CidadeServiceImpl(CidadeRepository cidadeRepository, CidadeMapper cidadeMapper) {
        this.cidadeRepository = cidadeRepository;
        this.cidadeMapper = cidadeMapper;
    }

    /**
     * Save a cidade.
     *
     * @param cidadeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CidadeDTO save(CidadeDTO cidadeDTO) {
        log.debug("Request to save Cidade : {}", cidadeDTO);
        Cidade cidade = cidadeMapper.toEntity(cidadeDTO);
        cidade = cidadeRepository.save(cidade);
        return cidadeMapper.toDto(cidade);
    }

    /**
     *  Get all the cidades.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CidadeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cidades");
        return cidadeRepository.findAll(pageable)
            .map(cidadeMapper::toDto);
    }

    /**
     *  Get one cidade by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CidadeDTO findOne(Long id) {
        log.debug("Request to get Cidade : {}", id);
        Cidade cidade = cidadeRepository.findOne(id);
        return cidadeMapper.toDto(cidade);
    }

    /**
     *  Delete the  cidade by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cidade : {}", id);
        cidadeRepository.delete(id);
    }
}
