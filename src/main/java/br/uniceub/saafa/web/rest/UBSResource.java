package br.uniceub.saafa.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.uniceub.saafa.service.UBSService;
import br.uniceub.saafa.web.rest.errors.BadRequestAlertException;
import br.uniceub.saafa.web.rest.util.HeaderUtil;
import br.uniceub.saafa.web.rest.util.PaginationUtil;
import br.uniceub.saafa.service.dto.UBSDTO;
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
 * REST controller for managing UBS.
 */
@RestController
@RequestMapping("/api")
public class UBSResource {

    private final Logger log = LoggerFactory.getLogger(UBSResource.class);

    private static final String ENTITY_NAME = "uBS";

    private final UBSService uBSService;

    public UBSResource(UBSService uBSService) {
        this.uBSService = uBSService;
    }

    /**
     * POST  /u-bs : Create a new uBS.
     *
     * @param uBSDTO the uBSDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new uBSDTO, or with status 400 (Bad Request) if the uBS has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/u-bs")
    @Timed
    public ResponseEntity<UBSDTO> createUBS(@Valid @RequestBody UBSDTO uBSDTO) throws URISyntaxException {
        log.debug("REST request to save UBS : {}", uBSDTO);
        if (uBSDTO.getId() != null) {
            throw new BadRequestAlertException("A new uBS cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UBSDTO result = uBSService.save(uBSDTO);
        return ResponseEntity.created(new URI("/api/u-bs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /u-bs : Updates an existing uBS.
     *
     * @param uBSDTO the uBSDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated uBSDTO,
     * or with status 400 (Bad Request) if the uBSDTO is not valid,
     * or with status 500 (Internal Server Error) if the uBSDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/u-bs")
    @Timed
    public ResponseEntity<UBSDTO> updateUBS(@Valid @RequestBody UBSDTO uBSDTO) throws URISyntaxException {
        log.debug("REST request to update UBS : {}", uBSDTO);
        if (uBSDTO.getId() == null) {
            return createUBS(uBSDTO);
        }
        UBSDTO result = uBSService.save(uBSDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, uBSDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /u-bs : get all the uBS.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of uBS in body
     */
    @GetMapping("/u-bs")
    @Timed
    public ResponseEntity<List<UBSDTO>> getAllUBS(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of UBS");
        Page<UBSDTO> page = uBSService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/u-bs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /u-bs/:id : get the "id" uBS.
     *
     * @param id the id of the uBSDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the uBSDTO, or with status 404 (Not Found)
     */
    @GetMapping("/u-bs/{id}")
    @Timed
    public ResponseEntity<UBSDTO> getUBS(@PathVariable Long id) {
        log.debug("REST request to get UBS : {}", id);
        UBSDTO uBSDTO = uBSService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(uBSDTO));
    }

    /**
     * DELETE  /u-bs/:id : delete the "id" uBS.
     *
     * @param id the id of the uBSDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/u-bs/{id}")
    @Timed
    public ResponseEntity<Void> deleteUBS(@PathVariable Long id) {
        log.debug("REST request to delete UBS : {}", id);
        uBSService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
