package br.uniceub.saafa.service.impl;

import br.uniceub.saafa.service.UBSService;
import br.uniceub.saafa.domain.UBS;
import br.uniceub.saafa.repository.UBSRepository;
import br.uniceub.saafa.service.dto.UBSDTO;
import br.uniceub.saafa.service.mapper.UBSMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing UBS.
 */
@Service
@Transactional
public class UBSServiceImpl implements UBSService{

    private final Logger log = LoggerFactory.getLogger(UBSServiceImpl.class);

    private final UBSRepository uBSRepository;

    private final UBSMapper uBSMapper;

    public UBSServiceImpl(UBSRepository uBSRepository, UBSMapper uBSMapper) {
        this.uBSRepository = uBSRepository;
        this.uBSMapper = uBSMapper;
    }

    /**
     * Save a uBS.
     *
     * @param uBSDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UBSDTO save(UBSDTO uBSDTO) {
        log.debug("Request to save UBS : {}", uBSDTO);
        UBS uBS = uBSMapper.toEntity(uBSDTO);
        uBS = uBSRepository.save(uBS);
        return uBSMapper.toDto(uBS);
    }

    /**
     *  Get all the uBS.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UBSDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UBS");
        return uBSRepository.findAll(pageable)
            .map(uBSMapper::toDto);
    }

    /**
     *  Get one uBS by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UBSDTO findOne(Long id) {
        log.debug("Request to get UBS : {}", id);
        UBS uBS = uBSRepository.findOne(id);
        return uBSMapper.toDto(uBS);
    }

    /**
     *  Delete the  uBS by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UBS : {}", id);
        uBSRepository.delete(id);
    }
}
