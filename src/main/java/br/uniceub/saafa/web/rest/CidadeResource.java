package br.uniceub.saafa.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.uniceub.saafa.service.CidadeService;
import br.uniceub.saafa.web.rest.errors.BadRequestAlertException;
import br.uniceub.saafa.web.rest.util.HeaderUtil;
import br.uniceub.saafa.web.rest.util.PaginationUtil;
import br.uniceub.saafa.service.dto.CidadeDTO;
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
 * REST controller for managing Cidade.
 */
@RestController
@RequestMapping("/api")
public class CidadeResource {

    private final Logger log = LoggerFactory.getLogger(CidadeResource.class);

    private static final String ENTITY_NAME = "cidade";

    private final CidadeService cidadeService;

    public CidadeResource(CidadeService cidadeService) {
        this.cidadeService = cidadeService;
    }

    /**
     * POST  /cidades : Create a new cidade.
     *
     * @param cidadeDTO the cidadeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cidadeDTO, or with status 400 (Bad Request) if the cidade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cidades")
    @Timed
    public ResponseEntity<CidadeDTO> createCidade(@Valid @RequestBody CidadeDTO cidadeDTO) throws URISyntaxException {
        log.debug("REST request to save Cidade : {}", cidadeDTO);
        if (cidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CidadeDTO result = cidadeService.save(cidadeDTO);
        return ResponseEntity.created(new URI("/api/cidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cidades : Updates an existing cidade.
     *
     * @param cidadeDTO the cidadeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cidadeDTO,
     * or with status 400 (Bad Request) if the cidadeDTO is not valid,
     * or with status 500 (Internal Server Error) if the cidadeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cidades")
    @Timed
    public ResponseEntity<CidadeDTO> updateCidade(@Valid @RequestBody CidadeDTO cidadeDTO) throws URISyntaxException {
        log.debug("REST request to update Cidade : {}", cidadeDTO);
        if (cidadeDTO.getId() == null) {
            return createCidade(cidadeDTO);
        }
        CidadeDTO result = cidadeService.save(cidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cidadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cidades : get all the cidades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cidades in body
     */
    @GetMapping("/cidades")
    @Timed
    public ResponseEntity<List<CidadeDTO>> getAllCidades(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Cidades");
        Page<CidadeDTO> page = cidadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cidades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cidades/:id : get the "id" cidade.
     *
     * @param id the id of the cidadeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cidadeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cidades/{id}")
    @Timed
    public ResponseEntity<CidadeDTO> getCidade(@PathVariable Long id) {
        log.debug("REST request to get Cidade : {}", id);
        CidadeDTO cidadeDTO = cidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cidadeDTO));
    }

    /**
     * DELETE  /cidades/:id : delete the "id" cidade.
     *
     * @param id the id of the cidadeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cidades/{id}")
    @Timed
    public ResponseEntity<Void> deleteCidade(@PathVariable Long id) {
        log.debug("REST request to delete Cidade : {}", id);
        cidadeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
