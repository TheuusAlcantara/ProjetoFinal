package br.uniceub.saafa.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.uniceub.saafa.service.FluxoAtendimentoService;
import br.uniceub.saafa.web.rest.errors.BadRequestAlertException;
import br.uniceub.saafa.web.rest.util.HeaderUtil;
import br.uniceub.saafa.web.rest.util.PaginationUtil;
import br.uniceub.saafa.service.dto.FluxoAtendimentoDTO;
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
 * REST controller for managing FluxoAtendimento.
 */
@RestController
@RequestMapping("/api")
public class FluxoAtendimentoResource {

    private final Logger log = LoggerFactory.getLogger(FluxoAtendimentoResource.class);

    private static final String ENTITY_NAME = "fluxoAtendimento";

    private final FluxoAtendimentoService fluxoAtendimentoService;

    public FluxoAtendimentoResource(FluxoAtendimentoService fluxoAtendimentoService) {
        this.fluxoAtendimentoService = fluxoAtendimentoService;
    }

    /**
     * POST  /fluxo-atendimentos : Create a new fluxoAtendimento.
     *
     * @param fluxoAtendimentoDTO the fluxoAtendimentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fluxoAtendimentoDTO, or with status 400 (Bad Request) if the fluxoAtendimento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fluxo-atendimentos")
    @Timed
    public ResponseEntity<FluxoAtendimentoDTO> createFluxoAtendimento(@Valid @RequestBody FluxoAtendimentoDTO fluxoAtendimentoDTO) throws URISyntaxException {
        log.debug("REST request to save FluxoAtendimento : {}", fluxoAtendimentoDTO);
        if (fluxoAtendimentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new fluxoAtendimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FluxoAtendimentoDTO result = fluxoAtendimentoService.save(fluxoAtendimentoDTO);
        return ResponseEntity.created(new URI("/api/fluxo-atendimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fluxo-atendimentos : Updates an existing fluxoAtendimento.
     *
     * @param fluxoAtendimentoDTO the fluxoAtendimentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fluxoAtendimentoDTO,
     * or with status 400 (Bad Request) if the fluxoAtendimentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the fluxoAtendimentoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fluxo-atendimentos")
    @Timed
    public ResponseEntity<FluxoAtendimentoDTO> updateFluxoAtendimento(@Valid @RequestBody FluxoAtendimentoDTO fluxoAtendimentoDTO) throws URISyntaxException {
        log.debug("REST request to update FluxoAtendimento : {}", fluxoAtendimentoDTO);
        if (fluxoAtendimentoDTO.getId() == null) {
            return createFluxoAtendimento(fluxoAtendimentoDTO);
        }
        FluxoAtendimentoDTO result = fluxoAtendimentoService.save(fluxoAtendimentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fluxoAtendimentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fluxo-atendimentos : get all the fluxoAtendimentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fluxoAtendimentos in body
     */
    @GetMapping("/fluxo-atendimentos")
    @Timed
    public ResponseEntity<List<FluxoAtendimentoDTO>> getAllFluxoAtendimentos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of FluxoAtendimentos");
        Page<FluxoAtendimentoDTO> page = fluxoAtendimentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fluxo-atendimentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fluxo-atendimentos/:id : get the "id" fluxoAtendimento.
     *
     * @param id the id of the fluxoAtendimentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fluxoAtendimentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/fluxo-atendimentos/{id}")
    @Timed
    public ResponseEntity<FluxoAtendimentoDTO> getFluxoAtendimento(@PathVariable Long id) {
        log.debug("REST request to get FluxoAtendimento : {}", id);
        FluxoAtendimentoDTO fluxoAtendimentoDTO = fluxoAtendimentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fluxoAtendimentoDTO));
    }

    /**
     * DELETE  /fluxo-atendimentos/:id : delete the "id" fluxoAtendimento.
     *
     * @param id the id of the fluxoAtendimentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fluxo-atendimentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteFluxoAtendimento(@PathVariable Long id) {
        log.debug("REST request to delete FluxoAtendimento : {}", id);
        fluxoAtendimentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
