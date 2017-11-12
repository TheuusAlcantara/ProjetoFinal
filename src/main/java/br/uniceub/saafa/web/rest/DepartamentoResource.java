package br.uniceub.saafa.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.uniceub.saafa.service.DepartamentoService;
import br.uniceub.saafa.web.rest.errors.BadRequestAlertException;
import br.uniceub.saafa.web.rest.util.HeaderUtil;
import br.uniceub.saafa.web.rest.util.PaginationUtil;
import br.uniceub.saafa.service.dto.DepartamentoDTO;
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
 * REST controller for managing Departamento.
 */
@RestController
@RequestMapping("/api")
public class DepartamentoResource {

    private final Logger log = LoggerFactory.getLogger(DepartamentoResource.class);

    private static final String ENTITY_NAME = "departamento";

    private final DepartamentoService departamentoService;

    public DepartamentoResource(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    /**
     * POST  /departamentos : Create a new departamento.
     *
     * @param departamentoDTO the departamentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new departamentoDTO, or with status 400 (Bad Request) if the departamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/departamentos")
    @Timed
    public ResponseEntity<DepartamentoDTO> createDepartamento(@Valid @RequestBody DepartamentoDTO departamentoDTO) throws URISyntaxException {
        log.debug("REST request to save Departamento : {}", departamentoDTO);
        if (departamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new departamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepartamentoDTO result = departamentoService.save(departamentoDTO);
        return ResponseEntity.created(new URI("/api/departamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /departamentos : Updates an existing departamento.
     *
     * @param departamentoDTO the departamentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated departamentoDTO,
     * or with status 400 (Bad Request) if the departamentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the departamentoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/departamentos")
    @Timed
    public ResponseEntity<DepartamentoDTO> updateDepartamento(@Valid @RequestBody DepartamentoDTO departamentoDTO) throws URISyntaxException {
        log.debug("REST request to update Departamento : {}", departamentoDTO);
        if (departamentoDTO.getId() == null) {
            return createDepartamento(departamentoDTO);
        }
        DepartamentoDTO result = departamentoService.save(departamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, departamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /departamentos : get all the departamentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of departamentos in body
     */
    @GetMapping("/departamentos")
    @Timed
    public ResponseEntity<List<DepartamentoDTO>> getAllDepartamentos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Departamentos");
        Page<DepartamentoDTO> page = departamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/departamentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /departamentos/:id : get the "id" departamento.
     *
     * @param id the id of the departamentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the departamentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/departamentos/{id}")
    @Timed
    public ResponseEntity<DepartamentoDTO> getDepartamento(@PathVariable Long id) {
        log.debug("REST request to get Departamento : {}", id);
        DepartamentoDTO departamentoDTO = departamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(departamentoDTO));
    }

    /**
     * DELETE  /departamentos/:id : delete the "id" departamento.
     *
     * @param id the id of the departamentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/departamentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteDepartamento(@PathVariable Long id) {
        log.debug("REST request to delete Departamento : {}", id);
        departamentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
