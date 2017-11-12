package br.uniceub.saafa.web.rest;

import br.uniceub.saafa.SaafaApp;

import br.uniceub.saafa.domain.FluxoAtendimento;
import br.uniceub.saafa.repository.FluxoAtendimentoRepository;
import br.uniceub.saafa.service.FluxoAtendimentoService;
import br.uniceub.saafa.service.dto.FluxoAtendimentoDTO;
import br.uniceub.saafa.service.mapper.FluxoAtendimentoMapper;
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
 * Test class for the FluxoAtendimentoResource REST controller.
 *
 * @see FluxoAtendimentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaafaApp.class)
public class FluxoAtendimentoResourceIntTest {

    private static final String DEFAULT_NOME_PACIENTE = "AAAAAAAAAA";
    private static final String UPDATED_NOME_PACIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_RAZAO_VISITA = "AAAAAAAAAA";
    private static final String UPDATED_RAZAO_VISITA = "BBBBBBBBBB";

    private static final String DEFAULT_HORA_CHEGADA = "AAAAAAAAAA";
    private static final String UPDATED_HORA_CHEGADA = "BBBBBBBBBB";

    @Autowired
    private FluxoAtendimentoRepository fluxoAtendimentoRepository;

    @Autowired
    private FluxoAtendimentoMapper fluxoAtendimentoMapper;

    @Autowired
    private FluxoAtendimentoService fluxoAtendimentoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFluxoAtendimentoMockMvc;

    private FluxoAtendimento fluxoAtendimento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FluxoAtendimentoResource fluxoAtendimentoResource = new FluxoAtendimentoResource(fluxoAtendimentoService);
        this.restFluxoAtendimentoMockMvc = MockMvcBuilders.standaloneSetup(fluxoAtendimentoResource)
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
    public static FluxoAtendimento createEntity(EntityManager em) {
        FluxoAtendimento fluxoAtendimento = new FluxoAtendimento()
            .nomePaciente(DEFAULT_NOME_PACIENTE)
            .razaoVisita(DEFAULT_RAZAO_VISITA)
            .horaChegada(DEFAULT_HORA_CHEGADA);
        return fluxoAtendimento;
    }

    @Before
    public void initTest() {
        fluxoAtendimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createFluxoAtendimento() throws Exception {
        int databaseSizeBeforeCreate = fluxoAtendimentoRepository.findAll().size();

        // Create the FluxoAtendimento
        FluxoAtendimentoDTO fluxoAtendimentoDTO = fluxoAtendimentoMapper.toDto(fluxoAtendimento);
        restFluxoAtendimentoMockMvc.perform(post("/api/fluxo-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fluxoAtendimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the FluxoAtendimento in the database
        List<FluxoAtendimento> fluxoAtendimentoList = fluxoAtendimentoRepository.findAll();
        assertThat(fluxoAtendimentoList).hasSize(databaseSizeBeforeCreate + 1);
        FluxoAtendimento testFluxoAtendimento = fluxoAtendimentoList.get(fluxoAtendimentoList.size() - 1);
        assertThat(testFluxoAtendimento.getNomePaciente()).isEqualTo(DEFAULT_NOME_PACIENTE);
        assertThat(testFluxoAtendimento.getRazaoVisita()).isEqualTo(DEFAULT_RAZAO_VISITA);
        assertThat(testFluxoAtendimento.getHoraChegada()).isEqualTo(DEFAULT_HORA_CHEGADA);
    }

    @Test
    @Transactional
    public void createFluxoAtendimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fluxoAtendimentoRepository.findAll().size();

        // Create the FluxoAtendimento with an existing ID
        fluxoAtendimento.setId(1L);
        FluxoAtendimentoDTO fluxoAtendimentoDTO = fluxoAtendimentoMapper.toDto(fluxoAtendimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFluxoAtendimentoMockMvc.perform(post("/api/fluxo-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fluxoAtendimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FluxoAtendimento in the database
        List<FluxoAtendimento> fluxoAtendimentoList = fluxoAtendimentoRepository.findAll();
        assertThat(fluxoAtendimentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomePacienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = fluxoAtendimentoRepository.findAll().size();
        // set the field null
        fluxoAtendimento.setNomePaciente(null);

        // Create the FluxoAtendimento, which fails.
        FluxoAtendimentoDTO fluxoAtendimentoDTO = fluxoAtendimentoMapper.toDto(fluxoAtendimento);

        restFluxoAtendimentoMockMvc.perform(post("/api/fluxo-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fluxoAtendimentoDTO)))
            .andExpect(status().isBadRequest());

        List<FluxoAtendimento> fluxoAtendimentoList = fluxoAtendimentoRepository.findAll();
        assertThat(fluxoAtendimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRazaoVisitaIsRequired() throws Exception {
        int databaseSizeBeforeTest = fluxoAtendimentoRepository.findAll().size();
        // set the field null
        fluxoAtendimento.setRazaoVisita(null);

        // Create the FluxoAtendimento, which fails.
        FluxoAtendimentoDTO fluxoAtendimentoDTO = fluxoAtendimentoMapper.toDto(fluxoAtendimento);

        restFluxoAtendimentoMockMvc.perform(post("/api/fluxo-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fluxoAtendimentoDTO)))
            .andExpect(status().isBadRequest());

        List<FluxoAtendimento> fluxoAtendimentoList = fluxoAtendimentoRepository.findAll();
        assertThat(fluxoAtendimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoraChegadaIsRequired() throws Exception {
        int databaseSizeBeforeTest = fluxoAtendimentoRepository.findAll().size();
        // set the field null
        fluxoAtendimento.setHoraChegada(null);

        // Create the FluxoAtendimento, which fails.
        FluxoAtendimentoDTO fluxoAtendimentoDTO = fluxoAtendimentoMapper.toDto(fluxoAtendimento);

        restFluxoAtendimentoMockMvc.perform(post("/api/fluxo-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fluxoAtendimentoDTO)))
            .andExpect(status().isBadRequest());

        List<FluxoAtendimento> fluxoAtendimentoList = fluxoAtendimentoRepository.findAll();
        assertThat(fluxoAtendimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFluxoAtendimentos() throws Exception {
        // Initialize the database
        fluxoAtendimentoRepository.saveAndFlush(fluxoAtendimento);

        // Get all the fluxoAtendimentoList
        restFluxoAtendimentoMockMvc.perform(get("/api/fluxo-atendimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fluxoAtendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomePaciente").value(hasItem(DEFAULT_NOME_PACIENTE.toString())))
            .andExpect(jsonPath("$.[*].razaoVisita").value(hasItem(DEFAULT_RAZAO_VISITA.toString())))
            .andExpect(jsonPath("$.[*].horaChegada").value(hasItem(DEFAULT_HORA_CHEGADA.toString())));
    }

    @Test
    @Transactional
    public void getFluxoAtendimento() throws Exception {
        // Initialize the database
        fluxoAtendimentoRepository.saveAndFlush(fluxoAtendimento);

        // Get the fluxoAtendimento
        restFluxoAtendimentoMockMvc.perform(get("/api/fluxo-atendimentos/{id}", fluxoAtendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fluxoAtendimento.getId().intValue()))
            .andExpect(jsonPath("$.nomePaciente").value(DEFAULT_NOME_PACIENTE.toString()))
            .andExpect(jsonPath("$.razaoVisita").value(DEFAULT_RAZAO_VISITA.toString()))
            .andExpect(jsonPath("$.horaChegada").value(DEFAULT_HORA_CHEGADA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFluxoAtendimento() throws Exception {
        // Get the fluxoAtendimento
        restFluxoAtendimentoMockMvc.perform(get("/api/fluxo-atendimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFluxoAtendimento() throws Exception {
        // Initialize the database
        fluxoAtendimentoRepository.saveAndFlush(fluxoAtendimento);
        int databaseSizeBeforeUpdate = fluxoAtendimentoRepository.findAll().size();

        // Update the fluxoAtendimento
        FluxoAtendimento updatedFluxoAtendimento = fluxoAtendimentoRepository.findOne(fluxoAtendimento.getId());
        updatedFluxoAtendimento
            .nomePaciente(UPDATED_NOME_PACIENTE)
            .razaoVisita(UPDATED_RAZAO_VISITA)
            .horaChegada(UPDATED_HORA_CHEGADA);
        FluxoAtendimentoDTO fluxoAtendimentoDTO = fluxoAtendimentoMapper.toDto(updatedFluxoAtendimento);

        restFluxoAtendimentoMockMvc.perform(put("/api/fluxo-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fluxoAtendimentoDTO)))
            .andExpect(status().isOk());

        // Validate the FluxoAtendimento in the database
        List<FluxoAtendimento> fluxoAtendimentoList = fluxoAtendimentoRepository.findAll();
        assertThat(fluxoAtendimentoList).hasSize(databaseSizeBeforeUpdate);
        FluxoAtendimento testFluxoAtendimento = fluxoAtendimentoList.get(fluxoAtendimentoList.size() - 1);
        assertThat(testFluxoAtendimento.getNomePaciente()).isEqualTo(UPDATED_NOME_PACIENTE);
        assertThat(testFluxoAtendimento.getRazaoVisita()).isEqualTo(UPDATED_RAZAO_VISITA);
        assertThat(testFluxoAtendimento.getHoraChegada()).isEqualTo(UPDATED_HORA_CHEGADA);
    }

    @Test
    @Transactional
    public void updateNonExistingFluxoAtendimento() throws Exception {
        int databaseSizeBeforeUpdate = fluxoAtendimentoRepository.findAll().size();

        // Create the FluxoAtendimento
        FluxoAtendimentoDTO fluxoAtendimentoDTO = fluxoAtendimentoMapper.toDto(fluxoAtendimento);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFluxoAtendimentoMockMvc.perform(put("/api/fluxo-atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fluxoAtendimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the FluxoAtendimento in the database
        List<FluxoAtendimento> fluxoAtendimentoList = fluxoAtendimentoRepository.findAll();
        assertThat(fluxoAtendimentoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFluxoAtendimento() throws Exception {
        // Initialize the database
        fluxoAtendimentoRepository.saveAndFlush(fluxoAtendimento);
        int databaseSizeBeforeDelete = fluxoAtendimentoRepository.findAll().size();

        // Get the fluxoAtendimento
        restFluxoAtendimentoMockMvc.perform(delete("/api/fluxo-atendimentos/{id}", fluxoAtendimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FluxoAtendimento> fluxoAtendimentoList = fluxoAtendimentoRepository.findAll();
        assertThat(fluxoAtendimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FluxoAtendimento.class);
        FluxoAtendimento fluxoAtendimento1 = new FluxoAtendimento();
        fluxoAtendimento1.setId(1L);
        FluxoAtendimento fluxoAtendimento2 = new FluxoAtendimento();
        fluxoAtendimento2.setId(fluxoAtendimento1.getId());
        assertThat(fluxoAtendimento1).isEqualTo(fluxoAtendimento2);
        fluxoAtendimento2.setId(2L);
        assertThat(fluxoAtendimento1).isNotEqualTo(fluxoAtendimento2);
        fluxoAtendimento1.setId(null);
        assertThat(fluxoAtendimento1).isNotEqualTo(fluxoAtendimento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FluxoAtendimentoDTO.class);
        FluxoAtendimentoDTO fluxoAtendimentoDTO1 = new FluxoAtendimentoDTO();
        fluxoAtendimentoDTO1.setId(1L);
        FluxoAtendimentoDTO fluxoAtendimentoDTO2 = new FluxoAtendimentoDTO();
        assertThat(fluxoAtendimentoDTO1).isNotEqualTo(fluxoAtendimentoDTO2);
        fluxoAtendimentoDTO2.setId(fluxoAtendimentoDTO1.getId());
        assertThat(fluxoAtendimentoDTO1).isEqualTo(fluxoAtendimentoDTO2);
        fluxoAtendimentoDTO2.setId(2L);
        assertThat(fluxoAtendimentoDTO1).isNotEqualTo(fluxoAtendimentoDTO2);
        fluxoAtendimentoDTO1.setId(null);
        assertThat(fluxoAtendimentoDTO1).isNotEqualTo(fluxoAtendimentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fluxoAtendimentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fluxoAtendimentoMapper.fromId(null)).isNull();
    }
}
