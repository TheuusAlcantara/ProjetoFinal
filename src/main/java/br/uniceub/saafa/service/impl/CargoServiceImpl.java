package br.uniceub.saafa.service.impl;

import br.uniceub.saafa.service.CargoService;
import br.uniceub.saafa.domain.Cargo;
import br.uniceub.saafa.repository.CargoRepository;
import br.uniceub.saafa.service.dto.CargoDTO;
import br.uniceub.saafa.service.mapper.CargoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Cargo.
 */
@Service
@Transactional
public class CargoServiceImpl implements CargoService{

    private final Logger log = LoggerFactory.getLogger(CargoServiceImpl.class);

    private final CargoRepository cargoRepository;

    private final CargoMapper cargoMapper;

    public CargoServiceImpl(CargoRepository cargoRepository, CargoMapper cargoMapper) {
        this.cargoRepository = cargoRepository;
        this.cargoMapper = cargoMapper;
    }

    /**
     * Save a cargo.
     *
     * @param cargoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CargoDTO save(CargoDTO cargoDTO) {
        log.debug("Request to save Cargo : {}", cargoDTO);
        Cargo cargo = cargoMapper.toEntity(cargoDTO);
        cargo = cargoRepository.save(cargo);
        return cargoMapper.toDto(cargo);
    }

    /**
     *  Get all the cargos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CargoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cargos");
        return cargoRepository.findAll(pageable)
            .map(cargoMapper::toDto);
    }

    /**
     *  Get one cargo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CargoDTO findOne(Long id) {
        log.debug("Request to get Cargo : {}", id);
        Cargo cargo = cargoRepository.findOne(id);
        return cargoMapper.toDto(cargo);
    }

    /**
     *  Delete the  cargo by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cargo : {}", id);
        cargoRepository.delete(id);
    }
}
