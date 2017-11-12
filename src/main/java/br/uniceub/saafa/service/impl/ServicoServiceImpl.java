package br.uniceub.saafa.service.impl;

import br.uniceub.saafa.service.ServicoService;
import br.uniceub.saafa.domain.Servico;
import br.uniceub.saafa.repository.ServicoRepository;
import br.uniceub.saafa.service.dto.ServicoDTO;
import br.uniceub.saafa.service.mapper.ServicoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Servico.
 */
@Service
@Transactional
public class ServicoServiceImpl implements ServicoService{

    private final Logger log = LoggerFactory.getLogger(ServicoServiceImpl.class);

    private final ServicoRepository servicoRepository;

    private final ServicoMapper servicoMapper;

    public ServicoServiceImpl(ServicoRepository servicoRepository, ServicoMapper servicoMapper) {
        this.servicoRepository = servicoRepository;
        this.servicoMapper = servicoMapper;
    }

    /**
     * Save a servico.
     *
     * @param servicoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ServicoDTO save(ServicoDTO servicoDTO) {
        log.debug("Request to save Servico : {}", servicoDTO);
        Servico servico = servicoMapper.toEntity(servicoDTO);
        servico = servicoRepository.save(servico);
        return servicoMapper.toDto(servico);
    }

    /**
     *  Get all the servicos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServicoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Servicos");
        return servicoRepository.findAll(pageable)
            .map(servicoMapper::toDto);
    }

    /**
     *  Get one servico by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ServicoDTO findOne(Long id) {
        log.debug("Request to get Servico : {}", id);
        Servico servico = servicoRepository.findOne(id);
        return servicoMapper.toDto(servico);
    }

    /**
     *  Delete the  servico by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Servico : {}", id);
        servicoRepository.delete(id);
    }
}
