package br.uniceub.saafa.service.impl;

import br.uniceub.saafa.service.EnderecoService;
import br.uniceub.saafa.domain.Endereco;
import br.uniceub.saafa.repository.EnderecoRepository;
import br.uniceub.saafa.service.dto.EnderecoDTO;
import br.uniceub.saafa.service.mapper.EnderecoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Endereco.
 */
@Service
@Transactional
public class EnderecoServiceImpl implements EnderecoService{

    private final Logger log = LoggerFactory.getLogger(EnderecoServiceImpl.class);

    private final EnderecoRepository enderecoRepository;

    private final EnderecoMapper enderecoMapper;

    public EnderecoServiceImpl(EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    /**
     * Save a endereco.
     *
     * @param enderecoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EnderecoDTO save(EnderecoDTO enderecoDTO) {
        log.debug("Request to save Endereco : {}", enderecoDTO);
        Endereco endereco = enderecoMapper.toEntity(enderecoDTO);
        endereco = enderecoRepository.save(endereco);
        return enderecoMapper.toDto(endereco);
    }

    /**
     *  Get all the enderecos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EnderecoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Enderecos");
        return enderecoRepository.findAll(pageable)
            .map(enderecoMapper::toDto);
    }

    /**
     *  Get one endereco by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EnderecoDTO findOne(Long id) {
        log.debug("Request to get Endereco : {}", id);
        Endereco endereco = enderecoRepository.findOne(id);
        return enderecoMapper.toDto(endereco);
    }

    /**
     *  Delete the  endereco by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Endereco : {}", id);
        enderecoRepository.delete(id);
    }
}
