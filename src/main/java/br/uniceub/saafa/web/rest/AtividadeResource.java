package br.uniceub.saafa.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.uniceub.saafa.service.AtividadeService;
import br.uniceub.saafa.web.rest.errors.BadRequestAlertException;
import br.uniceub.saafa.web.rest.util.HeaderUtil;
import br.uniceub.saafa.web.rest.util.PaginationUtil;
import br.uniceub.saafa.service.dto.AtividadeDTO;
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
 * REST controller for managing Atividade.
 */
@RestController
@RequestMapping("/api")
public class AtividadeResource {

    private final Logger log = LoggerFactory.getLogger(AtividadeResource.class);

    private static final String ENTITY_NAME = "atividade";

    private final AtividadeService atividadeService;

    public AtividadeResource(AtividadeService atividadeService) {
        this.atividadeService = atividadeService;
    }

    /**
     * POST  /atividades : Create a new atividade.
     *
     * @param atividadeDTO the atividadeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new atividadeDTO, or with status 400 (Bad Request) if the atividade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/atividades")
    @Timed
    public ResponseEntity<AtividadeDTO> createAtividade(@Valid @RequestBody AtividadeDTO atividadeDTO) throws URISyntaxException {
        log.debug("REST request to save Atividade : {}", atividadeDTO);
        if (atividadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new atividade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AtividadeDTO result = atividadeService.save(atividadeDTO);
        return ResponseEntity.created(new URI("/api/atividades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /atividades : Updates an existing atividade.
     *
     * @param atividadeDTO the atividadeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated atividadeDTO,
     * or with status 400 (Bad Request) if the atividadeDTO is not valid,
     * or with status 500 (Internal Server Error) if the atividadeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/atividades")
    @Timed
    public ResponseEntity<AtividadeDTO> updateAtividade(@Valid @RequestBody AtividadeDTO atividadeDTO) throws URISyntaxException {
        log.debug("REST request to update Atividade : {}", atividadeDTO);
        if (atividadeDTO.getId() == null) {
            return createAtividade(atividadeDTO);
        }
        AtividadeDTO result = atividadeService.save(atividadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, atividadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /atividades : get all the atividades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of atividades in body
     */
    @GetMapping("/atividades")
    @Timed
    public ResponseEntity<List<AtividadeDTO>> getAllAtividades(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Atividades");
        Page<AtividadeDTO> page = atividadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/atividades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /atividades/:id : get the "id" atividade.
     *
     * @param id the id of the atividadeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the atividadeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/atividades/{id}")
    @Timed
    public ResponseEntity<AtividadeDTO> getAtividade(@PathVariable Long id) {
        log.debug("REST request to get Atividade : {}", id);
        AtividadeDTO atividadeDTO = atividadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(atividadeDTO));
    }

    /**
     * DELETE  /atividades/:id : delete the "id" atividade.
     *
     * @param id the id of the atividadeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/atividades/{id}")
    @Timed
    public ResponseEntity<Void> deleteAtividade(@PathVariable Long id) {
        log.debug("REST request to delete Atividade : {}", id);
        atividadeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
