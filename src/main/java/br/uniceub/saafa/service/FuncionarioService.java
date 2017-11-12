package br.uniceub.saafa.service;

import br.uniceub.saafa.service.dto.FuncionarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Funcionario.
 */
public interface FuncionarioService {

    /**
     * Save a funcionario.
     *
     * @param funcionarioDTO the entity to save
     * @return the persisted entity
     */
    FuncionarioDTO save(FuncionarioDTO funcionarioDTO);

    /**
     *  Get all the funcionarios.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<FuncionarioDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" funcionario.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    FuncionarioDTO findOne(Long id);

    /**
     *  Delete the "id" funcionario.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
