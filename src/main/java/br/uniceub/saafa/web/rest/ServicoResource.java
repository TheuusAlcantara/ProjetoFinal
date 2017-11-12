package br.uniceub.saafa.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.uniceub.saafa.service.ServicoService;
import br.uniceub.saafa.web.rest.errors.BadRequestAlertException;
import br.uniceub.saafa.web.rest.util.HeaderUtil;
import br.uniceub.saafa.web.rest.util.PaginationUtil;
import br.uniceub.saafa.service.dto.ServicoDTO;
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
 * REST controller for managing Servico.
 */
@RestController
@RequestMapping("/api")
public class ServicoResource {

    private final Logger log = LoggerFactory.getLogger(ServicoResource.class);

    private static final String ENTITY_NAME = "servico";

    private final ServicoService servicoService;

    public ServicoResource(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    /**
     * POST  /servicos : Create a new servico.
     *
     * @param servicoDTO the servicoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new servicoDTO, or with status 400 (Bad Request) if the servico has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/servicos")
    @Timed
    public ResponseEntity<ServicoDTO> createServico(@Valid @RequestBody ServicoDTO servicoDTO) throws URISyntaxException {
        log.debug("REST request to save Servico : {}", servicoDTO);
        if (servicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new servico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServicoDTO result = servicoService.save(servicoDTO);
        return ResponseEntity.created(new URI("/api/servicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /servicos : Updates an existing servico.
     *
     * @param servicoDTO the servicoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated servicoDTO,
     * or with status 400 (Bad Request) if the servicoDTO is not valid,
     * or with status 500 (Internal Server Error) if the servicoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/servicos")
    @Timed
    public ResponseEntity<ServicoDTO> updateServico(@Valid @RequestBody ServicoDTO servicoDTO) throws URISyntaxException {
        log.debug("REST request to update Servico : {}", servicoDTO);
        if (servicoDTO.getId() == null) {
            return createServico(servicoDTO);
        }
        ServicoDTO result = servicoService.save(servicoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, servicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /servicos : get all the servicos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of servicos in body
     */
    @GetMapping("/servicos")
    @Timed
    public ResponseEntity<List<ServicoDTO>> getAllServicos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Servicos");
        Page<ServicoDTO> page = servicoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/servicos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /servicos/:id : get the "id" servico.
     *
     * @param id the id of the servicoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the servicoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/servicos/{id}")
    @Timed
    public ResponseEntity<ServicoDTO> getServico(@PathVariable Long id) {
        log.debug("REST request to get Servico : {}", id);
        ServicoDTO servicoDTO = servicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(servicoDTO));
    }

    /**
     * DELETE  /servicos/:id : delete the "id" servico.
     *
     * @param id the id of the servicoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/servicos/{id}")
    @Timed
    public ResponseEntity<Void> deleteServico(@PathVariable Long id) {
        log.debug("REST request to delete Servico : {}", id);
        servicoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
