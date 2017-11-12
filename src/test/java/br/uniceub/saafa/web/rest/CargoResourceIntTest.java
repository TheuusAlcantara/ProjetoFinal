package br.uniceub.saafa.web.rest;

import br.uniceub.saafa.SaafaApp;

import br.uniceub.saafa.domain.Cargo;
import br.uniceub.saafa.repository.CargoRepository;
import br.uniceub.saafa.service.CargoService;
import br.uniceub.saafa.service.dto.CargoDTO;
import br.uniceub.saafa.service.mapper.CargoMapper;
import br.uniceub.saafa.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static br.uniceub.saafa.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CargoResource REST controller.
 *
 * @see CargoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaafaApp.class)
public class CargoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private CargoRepository cargoRepository;

    @Autowired
    private CargoMapper cargoMapper;

    @Autowired
    private CargoService cargoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCargoMockMvc;

    private Cargo cargo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CargoResource cargoResource = new CargoResource(cargoService);
        this.restCargoMockMvc = MockMvcBuilders.standaloneSetup(cargoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cargo createEntity(EntityManager em) {
        Cargo cargo = new Cargo()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO);
        return cargo;
    }

    @Before
    public void initTest() {
        cargo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCargo() throws Exception {
        int databaseSizeBeforeCreate = cargoRepository.findAll().size();

        // Create the Cargo
        CargoDTO cargoDTO = cargoMapper.toDto(cargo);
        restCargoMockMvc.perform(post("/api/cargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cargoDTO)))
            .andExpect(status().isCreated());

        // Validate the Cargo in the database
        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeCreate + 1);
        Cargo testCargo = cargoList.get(cargoList.size() - 1);
        assertThat(testCargo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCargo.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createCargoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cargoRepository.findAll().size();

        // Create the Cargo with an existing ID
        cargo.setId(1L);
        CargoDTO cargoDTO = cargoMapper.toDto(cargo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCargoMockMvc.perform(post("/api/cargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cargoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cargo in the database
        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cargoRepository.findAll().size();
        // set the field null
        cargo.setNome(null);

        // Create the Cargo, which fails.
        CargoDTO cargoDTO = cargoMapper.toDto(cargo);

        restCargoMockMvc.perform(post("/api/cargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cargoDTO)))
            .andExpect(status().isBadRequest());

        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCargos() throws Exception {
        // Initialize the database
        cargoRepository.saveAndFlush(cargo);

        // Get all the cargoList
        restCargoMockMvc.perform(get("/api/cargos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cargo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getCargo() throws Exception {
        // Initialize the database
        cargoRepository.saveAndFlush(cargo);

        // Get the cargo
        restCargoMockMvc.perform(get("/api/cargos/{id}", cargo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cargo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCargo() throws Exception {
        // Get the cargo
        restCargoMockMvc.perform(get("/api/cargos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCargo() throws Exception {
        // Initialize the database
        cargoRepository.saveAndFlush(cargo);
        int databaseSizeBeforeUpdate = cargoRepository.findAll().size();

        // Update the cargo
        Cargo updatedCargo = cargoRepository.findOne(cargo.getId());
        updatedCargo
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);
        CargoDTO cargoDTO = cargoMapper.toDto(updatedCargo);

        restCargoMockMvc.perform(put("/api/cargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cargoDTO)))
            .andExpect(status().isOk());

        // Validate the Cargo in the database
        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeUpdate);
        Cargo testCargo = cargoList.get(cargoList.size() - 1);
        assertThat(testCargo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCargo.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingCargo() throws Exception {
        int databaseSizeBeforeUpdate = cargoRepository.findAll().size();

        // Create the Cargo
        CargoDTO cargoDTO = cargoMapper.toDto(cargo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCargoMockMvc.perform(put("/api/cargos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cargoDTO)))
            .andExpect(status().isCreated());

        // Validate the Cargo in the database
        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCargo() throws Exception {
        // Initialize the database
        cargoRepository.saveAndFlush(cargo);
        int databaseSizeBeforeDelete = cargoRepository.findAll().size();

        // Get the cargo
        restCargoMockMvc.perform(delete("/api/cargos/{id}", cargo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cargo> cargoList = cargoRepository.findAll();
        assertThat(cargoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cargo.class);
        Cargo cargo1 = new Cargo();
        cargo1.setId(1L);
        Cargo cargo2 = new Cargo();
        cargo2.setId(cargo1.getId());
        assertThat(cargo1).isEqualTo(cargo2);
        cargo2.setId(2L);
        assertThat(cargo1).isNotEqualTo(cargo2);
        cargo1.setId(null);
        assertThat(cargo1).isNotEqualTo(cargo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CargoDTO.class);
        CargoDTO cargoDTO1 = new CargoDTO();
        cargoDTO1.setId(1L);
        CargoDTO cargoDTO2 = new CargoDTO();
        assertThat(cargoDTO1).isNotEqualTo(cargoDTO2);
        cargoDTO2.setId(cargoDTO1.getId());
        assertThat(cargoDTO1).isEqualTo(cargoDTO2);
        cargoDTO2.setId(2L);
        assertThat(cargoDTO1).isNotEqualTo(cargoDTO2);
        cargoDTO1.setId(null);
        assertThat(cargoDTO1).isNotEqualTo(cargoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cargoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cargoMapper.fromId(null)).isNull();
    }
}
