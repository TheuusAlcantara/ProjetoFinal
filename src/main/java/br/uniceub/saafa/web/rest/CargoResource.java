package br.uniceub.saafa.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.uniceub.saafa.service.CargoService;
import br.uniceub.saafa.web.rest.errors.BadRequestAlertException;
import br.uniceub.saafa.web.rest.util.HeaderUtil;
import br.uniceub.saafa.web.rest.util.PaginationUtil;
import br.uniceub.saafa.service.dto.CargoDTO;
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
 * REST controller for managing Cargo.
 */
@RestController
@RequestMapping("/api")
public class CargoResource {

    private final Logger log = LoggerFactory.getLogger(CargoResource.class);

    private static final String ENTITY_NAME = "cargo";

    private final CargoService cargoService;

    public CargoResource(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    /**
     * POST  /cargos : Create a new cargo.
     *
     * @param cargoDTO the cargoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cargoDTO, or with status 400 (Bad Request) if the cargo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cargos")
    @Timed
    public ResponseEntity<CargoDTO> createCargo(@Valid @RequestBody CargoDTO cargoDTO) throws URISyntaxException {
        log.debug("REST request to save Cargo : {}", cargoDTO);
        if (cargoDTO.getId() != null) {
            throw new BadRequestAlertException("A new cargo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CargoDTO result = cargoService.save(cargoDTO);
        return ResponseEntity.created(new URI("/api/cargos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cargos : Updates an existing cargo.
     *
     * @param cargoDTO the cargoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cargoDTO,
     * or with status 400 (Bad Request) if the cargoDTO is not valid,
     * or with status 500 (Internal Server Error) if the cargoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cargos")
    @Timed
    public ResponseEntity<CargoDTO> updateCargo(@Valid @RequestBody CargoDTO cargoDTO) throws URISyntaxException {
        log.debug("REST request to update Cargo : {}", cargoDTO);
        if (cargoDTO.getId() == null) {
            return createCargo(cargoDTO);
        }
        CargoDTO result = cargoService.save(cargoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cargoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cargos : get all the cargos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cargos in body
     */
    @GetMapping("/cargos")
    @Timed
    public ResponseEntity<List<CargoDTO>> getAllCargos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Cargos");
        Page<CargoDTO> page = cargoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cargos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cargos/:id : get the "id" cargo.
     *
     * @param id the id of the cargoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cargoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cargos/{id}")
    @Timed
    public ResponseEntity<CargoDTO> getCargo(@PathVariable Long id) {
        log.debug("REST request to get Cargo : {}", id);
        CargoDTO cargoDTO = cargoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cargoDTO));
    }

    /**
     * DELETE  /cargos/:id : delete the "id" cargo.
     *
     * @param id the id of the cargoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cargos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCargo(@PathVariable Long id) {
        log.debug("REST request to delete Cargo : {}", id);
        cargoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
