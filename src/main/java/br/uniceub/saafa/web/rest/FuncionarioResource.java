package br.uniceub.saafa.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.uniceub.saafa.service.FuncionarioService;
import br.uniceub.saafa.web.rest.errors.BadRequestAlertException;
import br.uniceub.saafa.web.rest.util.HeaderUtil;
import br.uniceub.saafa.web.rest.util.PaginationUtil;
import br.uniceub.saafa.service.dto.FuncionarioDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Funcionario.
 */
@RestController
@RequestMapping("/api")
public class FuncionarioResource {

    private final Logger log = LoggerFactory.getLogger(FuncionarioResource.class);

    private static final String ENTITY_NAME = "funcionario";

    private final FuncionarioService funcionarioService;

    public FuncionarioResource(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    /**
     * POST  /funcionarios : Create a new funcionario.
     *
     * @param funcionarioDTO the funcionarioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new funcionarioDTO, or with status 400 (Bad Request) if the funcionario has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/funcionarios")
    @Timed
    public ResponseEntity<FuncionarioDTO> createFuncionario(@Valid @RequestBody FuncionarioDTO funcionarioDTO) throws URISyntaxException {
        log.debug("REST request to save Funcionario : {}", funcionarioDTO);
        if (funcionarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new funcionario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FuncionarioDTO result = funcionarioService.save(funcionarioDTO);
        return ResponseEntity.created(new URI("/api/funcionarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /funcionarios : Updates an existing funcionario.
     *
     * @param funcionarioDTO the funcionarioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated funcionarioDTO,
     * or with status 400 (Bad Request) if the funcionarioDTO is not valid,
     * or with status 500 (Internal Server Error) if the funcionarioDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/funcionarios")
    @Timed
    public ResponseEntity<FuncionarioDTO> updateFuncionario(@Valid @RequestBody FuncionarioDTO funcionarioDTO) throws URISyntaxException {
        log.debug("REST request to update Funcionario : {}", funcionarioDTO);
        if (funcionarioDTO.getId() == null) {
            return createFuncionario(funcionarioDTO);
        }
        FuncionarioDTO result = funcionarioService.save(funcionarioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, funcionarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /funcionarios : get all the funcionarios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of funcionarios in body
     */
    @GetMapping("/funcionarios")
    @Timed
    public ResponseEntity<List<FuncionarioDTO>> getAllFuncionarios(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Funcionarios");
        Page<FuncionarioDTO> page = funcionarioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/funcionarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /funcionarios/:id : get the "id" funcionario.
     *
     * @param id the id of the funcionarioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the funcionarioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/funcionarios/{id}")
    @Timed
    public ResponseEntity<FuncionarioDTO> getFuncionario(@PathVariable Long id) {
        log.debug("REST request to get Funcionario : {}", id);
        FuncionarioDTO funcionarioDTO = funcionarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(funcionarioDTO));
    }

    /**
     * DELETE  /funcionarios/:id : delete the "id" funcionario.
     *
     * @param id the id of the funcionarioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/funcionarios/{id}")
    @Timed
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Long id) {
        log.debug("REST request to delete Funcionario : {}", id);
        funcionarioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
