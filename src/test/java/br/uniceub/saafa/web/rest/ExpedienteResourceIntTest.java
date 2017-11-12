package br.uniceub.saafa.web.rest;

import br.uniceub.saafa.SaafaApp;

import br.uniceub.saafa.domain.Expediente;
import br.uniceub.saafa.repository.ExpedienteRepository;
import br.uniceub.saafa.service.ExpedienteService;
import br.uniceub.saafa.service.dto.ExpedienteDTO;
import br.uniceub.saafa.service.mapper.ExpedienteMapper;
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

import br.uniceub.saafa.domain.enumeration.DiaSemana;
/**
 * Test class for the ExpedienteResource REST controller.
 *
 * @see ExpedienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaafaApp.class)
public class ExpedienteResourceIntTest {

    private static final String DEFAULT_HORARIO_ENTRADA = "AAAAAAAAAA";
    private static final String UPDATED_HORARIO_ENTRADA = "BBBBBBBBBB";

    private static final String DEFAULT_HORARIO_SAIDA = "AAAAAAAAAA";
    private static final String UPDATED_HORARIO_SAIDA = "BBBBBBBBBB";

    private static final DiaSemana DEFAULT_DIA_SEMANA = DiaSemana.DOMINGO;
    private static final DiaSemana UPDATED_DIA_SEMANA = DiaSemana.SEGUNDA;

    @Autowired
    private ExpedienteRepository expedienteRepository;

    @Autowired
    private ExpedienteMapper expedienteMapper;

    @Autowired
    private ExpedienteService expedienteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExpedienteMockMvc;

    private Expediente expediente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExpedienteResource expedienteResource = new ExpedienteResource(expedienteService);
        this.restExpedienteMockMvc = MockMvcBuilders.standaloneSetup(expedienteResource)
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
    public static Expediente createEntity(EntityManager em) {
        Expediente expediente = new Expediente()
            .horarioEntrada(DEFAULT_HORARIO_ENTRADA)
            .horarioSaida(DEFAULT_HORARIO_SAIDA)
            .diaSemana(DEFAULT_DIA_SEMANA);
        return expediente;
    }

    @Before
    public void initTest() {
        expediente = createEntity(em);
    }

    @Test
    @Transactional
    public void createExpediente() throws Exception {
        int databaseSizeBeforeCreate = expedienteRepository.findAll().size();

        // Create the Expediente
        ExpedienteDTO expedienteDTO = expedienteMapper.toDto(expediente);
        restExpedienteMockMvc.perform(post("/api/expedientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expedienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Expediente in the database
        List<Expediente> expedienteList = expedienteRepository.findAll();
        assertThat(expedienteList).hasSize(databaseSizeBeforeCreate + 1);
        Expediente testExpediente = expedienteList.get(expedienteList.size() - 1);
        assertThat(testExpediente.getHorarioEntrada()).isEqualTo(DEFAULT_HORARIO_ENTRADA);
        assertThat(testExpediente.getHorarioSaida()).isEqualTo(DEFAULT_HORARIO_SAIDA);
        assertThat(testExpediente.getDiaSemana()).isEqualTo(DEFAULT_DIA_SEMANA);
    }

    @Test
    @Transactional
    public void createExpedienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = expedienteRepository.findAll().size();

        // Create the Expediente with an existing ID
        expediente.setId(1L);
        ExpedienteDTO expedienteDTO = expedienteMapper.toDto(expediente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpedienteMockMvc.perform(post("/api/expedientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expedienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Expediente in the database
        List<Expediente> expedienteList = expedienteRepository.findAll();
        assertThat(expedienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkHorarioEntradaIsRequired() throws Exception {
        int databaseSizeBeforeTest = expedienteRepository.findAll().size();
        // set the field null
        expediente.setHorarioEntrada(null);

        // Create the Expediente, which fails.
        ExpedienteDTO expedienteDTO = expedienteMapper.toDto(expediente);

        restExpedienteMockMvc.perform(post("/api/expedientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expedienteDTO)))
            .andExpect(status().isBadRequest());

        List<Expediente> expedienteList = expedienteRepository.findAll();
        assertThat(expedienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHorarioSaidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = expedienteRepository.findAll().size();
        // set the field null
        expediente.setHorarioSaida(null);

        // Create the Expediente, which fails.
        ExpedienteDTO expedienteDTO = expedienteMapper.toDto(expediente);

        restExpedienteMockMvc.perform(post("/api/expedientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expedienteDTO)))
            .andExpect(status().isBadRequest());

        List<Expediente> expedienteList = expedienteRepository.findAll();
        assertThat(expedienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiaSemanaIsRequired() throws Exception {
        int databaseSizeBeforeTest = expedienteRepository.findAll().size();
        // set the field null
        expediente.setDiaSemana(null);

        // Create the Expediente, which fails.
        ExpedienteDTO expedienteDTO = expedienteMapper.toDto(expediente);

        restExpedienteMockMvc.perform(post("/api/expedientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expedienteDTO)))
            .andExpect(status().isBadRequest());

        List<Expediente> expedienteList = expedienteRepository.findAll();
        assertThat(expedienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExpedientes() throws Exception {
        // Initialize the database
        expedienteRepository.saveAndFlush(expediente);

        // Get all the expedienteList
        restExpedienteMockMvc.perform(get("/api/expedientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expediente.getId().intValue())))
            .andExpect(jsonPath("$.[*].horarioEntrada").value(hasItem(DEFAULT_HORARIO_ENTRADA.toString())))
            .andExpect(jsonPath("$.[*].horarioSaida").value(hasItem(DEFAULT_HORARIO_SAIDA.toString())))
            .andExpect(jsonPath("$.[*].diaSemana").value(hasItem(DEFAULT_DIA_SEMANA.toString())));
    }

    @Test
    @Transactional
    public void getExpediente() throws Exception {
        // Initialize the database
        expedienteRepository.saveAndFlush(expediente);

        // Get the expediente
        restExpedienteMockMvc.perform(get("/api/expedientes/{id}", expediente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(expediente.getId().intValue()))
            .andExpect(jsonPath("$.horarioEntrada").value(DEFAULT_HORARIO_ENTRADA.toString()))
            .andExpect(jsonPath("$.horarioSaida").value(DEFAULT_HORARIO_SAIDA.toString()))
            .andExpect(jsonPath("$.diaSemana").value(DEFAULT_DIA_SEMANA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExpediente() throws Exception {
        // Get the expediente
        restExpedienteMockMvc.perform(get("/api/expedientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExpediente() throws Exception {
        // Initialize the database
        expedienteRepository.saveAndFlush(expediente);
        int databaseSizeBeforeUpdate = expedienteRepository.findAll().size();

        // Update the expediente
        Expediente updatedExpediente = expedienteRepository.findOne(expediente.getId());
        updatedExpediente
            .horarioEntrada(UPDATED_HORARIO_ENTRADA)
            .horarioSaida(UPDATED_HORARIO_SAIDA)
            .diaSemana(UPDATED_DIA_SEMANA);
        ExpedienteDTO expedienteDTO = expedienteMapper.toDto(updatedExpediente);

        restExpedienteMockMvc.perform(put("/api/expedientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expedienteDTO)))
            .andExpect(status().isOk());

        // Validate the Expediente in the database
        List<Expediente> expedienteList = expedienteRepository.findAll();
        assertThat(expedienteList).hasSize(databaseSizeBeforeUpdate);
        Expediente testExpediente = expedienteList.get(expedienteList.size() - 1);
        assertThat(testExpediente.getHorarioEntrada()).isEqualTo(UPDATED_HORARIO_ENTRADA);
        assertThat(testExpediente.getHorarioSaida()).isEqualTo(UPDATED_HORARIO_SAIDA);
        assertThat(testExpediente.getDiaSemana()).isEqualTo(UPDATED_DIA_SEMANA);
    }

    @Test
    @Transactional
    public void updateNonExistingExpediente() throws Exception {
        int databaseSizeBeforeUpdate = expedienteRepository.findAll().size();

        // Create the Expediente
        ExpedienteDTO expedienteDTO = expedienteMapper.toDto(expediente);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExpedienteMockMvc.perform(put("/api/expedientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(expedienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Expediente in the database
        List<Expediente> expedienteList = expedienteRepository.findAll();
        assertThat(expedienteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExpediente() throws Exception {
        // Initialize the database
        expedienteRepository.saveAndFlush(expediente);
        int databaseSizeBeforeDelete = expedienteRepository.findAll().size();

        // Get the expediente
        restExpedienteMockMvc.perform(delete("/api/expedientes/{id}", expediente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Expediente> expedienteList = expedienteRepository.findAll();
        assertThat(expedienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Expediente.class);
        Expediente expediente1 = new Expediente();
        expediente1.setId(1L);
        Expediente expediente2 = new Expediente();
        expediente2.setId(expediente1.getId());
        assertThat(expediente1).isEqualTo(expediente2);
        expediente2.setId(2L);
        assertThat(expediente1).isNotEqualTo(expediente2);
        expediente1.setId(null);
        assertThat(expediente1).isNotEqualTo(expediente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExpedienteDTO.class);
        ExpedienteDTO expedienteDTO1 = new ExpedienteDTO();
        expedienteDTO1.setId(1L);
        ExpedienteDTO expedienteDTO2 = new ExpedienteDTO();
        assertThat(expedienteDTO1).isNotEqualTo(expedienteDTO2);
        expedienteDTO2.setId(expedienteDTO1.getId());
        assertThat(expedienteDTO1).isEqualTo(expedienteDTO2);
        expedienteDTO2.setId(2L);
        assertThat(expedienteDTO1).isNotEqualTo(expedienteDTO2);
        expedienteDTO1.setId(null);
        assertThat(expedienteDTO1).isNotEqualTo(expedienteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(expedienteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(expedienteMapper.fromId(null)).isNull();
    }
}
