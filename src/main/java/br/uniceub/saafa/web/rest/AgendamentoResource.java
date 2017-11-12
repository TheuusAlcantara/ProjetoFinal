package br.uniceub.saafa.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.uniceub.saafa.service.AgendamentoService;
import br.uniceub.saafa.web.rest.errors.BadRequestAlertException;
import br.uniceub.saafa.web.rest.util.HeaderUtil;
import br.uniceub.saafa.web.rest.util.PaginationUtil;
import br.uniceub.saafa.service.dto.AgendamentoDTO;
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
 * REST controller for managing Agendamento.
 */
@RestController
@RequestMapping("/api")
public class AgendamentoResource {

    private final Logger log = LoggerFactory.getLogger(AgendamentoResource.class);

    private static final String ENTITY_NAME = "agendamento";

    private final AgendamentoService agendamentoService;

    public AgendamentoResource(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    /**
     * POST  /agendamentos : Create a new agendamento.
     *
     * @param agendamentoDTO the agendamentoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new agendamentoDTO, or with status 400 (Bad Request) if the agendamento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/agendamentos")
    @Timed
    public ResponseEntity<AgendamentoDTO> createAgendamento(@Valid @RequestBody AgendamentoDTO agendamentoDTO) throws URISyntaxException {
        log.debug("REST request to save Agendamento : {}", agendamentoDTO);
        if (agendamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new agendamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgendamentoDTO result = agendamentoService.save(agendamentoDTO);
        return ResponseEntity.created(new URI("/api/agendamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /agendamentos : Updates an existing agendamento.
     *
     * @param agendamentoDTO the agendamentoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated agendamentoDTO,
     * or with status 400 (Bad Request) if the agendamentoDTO is not valid,
     * or with status 500 (Internal Server Error) if the agendamentoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/agendamentos")
    @Timed
    public ResponseEntity<AgendamentoDTO> updateAgendamento(@Valid @RequestBody AgendamentoDTO agendamentoDTO) throws URISyntaxException {
        log.debug("REST request to update Agendamento : {}", agendamentoDTO);
        if (agendamentoDTO.getId() == null) {
            return createAgendamento(agendamentoDTO);
        }
        AgendamentoDTO result = agendamentoService.save(agendamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, agendamentoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /agendamentos : get all the agendamentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of agendamentos in body
     */
    @GetMapping("/agendamentos")
    @Timed
    public ResponseEntity<List<AgendamentoDTO>> getAllAgendamentos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Agendamentos");
        Page<AgendamentoDTO> page = agendamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/agendamentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /agendamentos/:id : get the "id" agendamento.
     *
     * @param id the id of the agendamentoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the agendamentoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/agendamentos/{id}")
    @Timed
    public ResponseEntity<AgendamentoDTO> getAgendamento(@PathVariable Long id) {
        log.debug("REST request to get Agendamento : {}", id);
        AgendamentoDTO agendamentoDTO = agendamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(agendamentoDTO));
    }

    /**
     * DELETE  /agendamentos/:id : delete the "id" agendamento.
     *
     * @param id the id of the agendamentoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/agendamentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAgendamento(@PathVariable Long id) {
        log.debug("REST request to delete Agendamento : {}", id);
        agendamentoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
