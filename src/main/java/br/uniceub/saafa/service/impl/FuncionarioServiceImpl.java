package br.uniceub.saafa.service.impl;

import br.uniceub.saafa.service.FuncionarioService;
import br.uniceub.saafa.domain.Funcionario;
import br.uniceub.saafa.repository.FuncionarioRepository;
import br.uniceub.saafa.service.dto.FuncionarioDTO;
import br.uniceub.saafa.service.mapper.FuncionarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Funcionario.
 */
@Service
@Transactional
public class FuncionarioServiceImpl implements FuncionarioService{

    private final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

    private final FuncionarioRepository funcionarioRepository;

    private final FuncionarioMapper funcionarioMapper;

    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
    }

    /**
     * Save a funcionario.
     *
     * @param funcionarioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FuncionarioDTO save(FuncionarioDTO funcionarioDTO) {
        log.debug("Request to save Funcionario : {}", funcionarioDTO);
        Funcionario funcionario = funcionarioMapper.toEntity(funcionarioDTO);
        funcionario = funcionarioRepository.save(funcionario);
        return funcionarioMapper.toDto(funcionario);
    }

    /**
     *  Get all the funcionarios.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FuncionarioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Funcionarios");
        return funcionarioRepository.findAll(pageable)
            .map(funcionarioMapper::toDto);
    }

    /**
     *  Get one funcionario by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FuncionarioDTO findOne(Long id) {
        log.debug("Request to get Funcionario : {}", id);
        Funcionario funcionario = funcionarioRepository.findOne(id);
        return funcionarioMapper.toDto(funcionario);
    }

    /**
     *  Delete the  funcionario by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Funcionario : {}", id);
        funcionarioRepository.delete(id);
    }
}
