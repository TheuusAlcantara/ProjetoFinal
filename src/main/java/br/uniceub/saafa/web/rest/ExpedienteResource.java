package br.uniceub.saafa.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.uniceub.saafa.service.ExpedienteService;
import br.uniceub.saafa.web.rest.errors.BadRequestAlertException;
import br.uniceub.saafa.web.rest.util.HeaderUtil;
import br.uniceub.saafa.web.rest.util.PaginationUtil;
import br.uniceub.saafa.service.dto.ExpedienteDTO;
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
 * REST controller for managing Expediente.
 */
@RestController
@RequestMapping("/api")
public class ExpedienteResource {

    private final Logger log = LoggerFactory.getLogger(ExpedienteResource.class);

    private static final String ENTITY_NAME = "expediente";

    private final ExpedienteService expedienteService;

    public ExpedienteResource(ExpedienteService expedienteService) {
        this.expedienteService = expedienteService;
    }

    /**
     * POST  /expedientes : Create a new expediente.
     *
     * @param expedienteDTO the expedienteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new expedienteDTO, or with status 400 (Bad Request) if the expediente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/expedientes")
    @Timed
    public ResponseEntity<ExpedienteDTO> createExpediente(@Valid @RequestBody ExpedienteDTO expedienteDTO) throws URISyntaxException {
        log.debug("REST request to save Expediente : {}", expedienteDTO);
        if (expedienteDTO.getId() != null) {
            throw new BadRequestAlertException("A new expediente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExpedienteDTO result = expedienteService.save(expedienteDTO);
        return ResponseEntity.created(new URI("/api/expedientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /expedientes : Updates an existing expediente.
     *
     * @param expedienteDTO the expedienteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated expedienteDTO,
     * or with status 400 (Bad Request) if the expedienteDTO is not valid,
     * or with status 500 (Internal Server Error) if the expedienteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/expedientes")
    @Timed
    public ResponseEntity<ExpedienteDTO> updateExpediente(@Valid @RequestBody ExpedienteDTO expedienteDTO) throws URISyntaxException {
        log.debug("REST request to update Expediente : {}", expedienteDTO);
        if (expedienteDTO.getId() == null) {
            return createExpediente(expedienteDTO);
        }
        ExpedienteDTO result = expedienteService.save(expedienteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, expedienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /expedientes : get all the expedientes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of expedientes in body
     */
    @GetMapping("/expedientes")
    @Timed
    public ResponseEntity<List<ExpedienteDTO>> getAllExpedientes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Expedientes");
        Page<ExpedienteDTO> page = expedienteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/expedientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /expedientes/:id : get the "id" expediente.
     *
     * @param id the id of the expedienteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the expedienteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/expedientes/{id}")
    @Timed
    public ResponseEntity<ExpedienteDTO> getExpediente(@PathVariable Long id) {
        log.debug("REST request to get Expediente : {}", id);
        ExpedienteDTO expedienteDTO = expedienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(expedienteDTO));
    }

    /**
     * DELETE  /expedientes/:id : delete the "id" expediente.
     *
     * @param id the id of the expedienteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/expedientes/{id}")
    @Timed
    public ResponseEntity<Void> deleteExpediente(@PathVariable Long id) {
        log.debug("REST request to delete Expediente : {}", id);
        expedienteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
