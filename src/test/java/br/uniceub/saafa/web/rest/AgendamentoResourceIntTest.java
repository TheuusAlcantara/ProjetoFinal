package br.uniceub.saafa.web.rest;

import br.uniceub.saafa.SaafaApp;

import br.uniceub.saafa.domain.Agendamento;
import br.uniceub.saafa.repository.AgendamentoRepository;
import br.uniceub.saafa.service.AgendamentoService;
import br.uniceub.saafa.service.dto.AgendamentoDTO;
import br.uniceub.saafa.service.mapper.AgendamentoMapper;
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
 * Test class for the AgendamentoResource REST controller.
 *
 * @see AgendamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaafaApp.class)
public class AgendamentoResourceIntTest {

    private static final String DEFAULT_HORARIO = "AAAAAAAAAA";
    private static final String UPDATED_HORARIO = "BBBBBBBBBB";

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private AgendamentoMapper agendamentoMapper;

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAgendamentoMockMvc;

    private Agendamento agendamento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgendamentoResource agendamentoResource = new AgendamentoResource(agendamentoService);
        this.restAgendamentoMockMvc = MockMvcBuilders.standaloneSetup(agendamentoResource)
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
    public static Agendamento createEntity(EntityManager em) {
        Agendamento agendamento = new Agendamento()
            .horario(DEFAULT_HORARIO);
        return agendamento;
    }

    @Before
    public void initTest() {
        agendamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgendamento() throws Exception {
        int databaseSizeBeforeCreate = agendamentoRepository.findAll().size();

        // Create the Agendamento
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);
        restAgendamentoMockMvc.perform(post("/api/agendamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Agendamento in the database
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Agendamento testAgendamento = agendamentoList.get(agendamentoList.size() - 1);
        assertThat(testAgendamento.getHorario()).isEqualTo(DEFAULT_HORARIO);
    }

    @Test
    @Transactional
    public void createAgendamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agendamentoRepository.findAll().size();

        // Create the Agendamento with an existing ID
        agendamento.setId(1L);
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendamentoMockMvc.perform(post("/api/agendamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agendamento in the database
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkHorarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = agendamentoRepository.findAll().size();
        // set the field null
        agendamento.setHorario(null);

        // Create the Agendamento, which fails.
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        restAgendamentoMockMvc.perform(post("/api/agendamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgendamentos() throws Exception {
        // Initialize the database
        agendamentoRepository.saveAndFlush(agendamento);

        // Get all the agendamentoList
        restAgendamentoMockMvc.perform(get("/api/agendamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agendamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].horario").value(hasItem(DEFAULT_HORARIO.toString())));
    }

    @Test
    @Transactional
    public void getAgendamento() throws Exception {
        // Initialize the database
        agendamentoRepository.saveAndFlush(agendamento);

        // Get the agendamento
        restAgendamentoMockMvc.perform(get("/api/agendamentos/{id}", agendamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agendamento.getId().intValue()))
            .andExpect(jsonPath("$.horario").value(DEFAULT_HORARIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAgendamento() throws Exception {
        // Get the agendamento
        restAgendamentoMockMvc.perform(get("/api/agendamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgendamento() throws Exception {
        // Initialize the database
        agendamentoRepository.saveAndFlush(agendamento);
        int databaseSizeBeforeUpdate = agendamentoRepository.findAll().size();

        // Update the agendamento
        Agendamento updatedAgendamento = agendamentoRepository.findOne(agendamento.getId());
        updatedAgendamento
            .horario(UPDATED_HORARIO);
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(updatedAgendamento);

        restAgendamentoMockMvc.perform(put("/api/agendamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendamentoDTO)))
            .andExpect(status().isOk());

        // Validate the Agendamento in the database
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeUpdate);
        Agendamento testAgendamento = agendamentoList.get(agendamentoList.size() - 1);
        assertThat(testAgendamento.getHorario()).isEqualTo(UPDATED_HORARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingAgendamento() throws Exception {
        int databaseSizeBeforeUpdate = agendamentoRepository.findAll().size();

        // Create the Agendamento
        AgendamentoDTO agendamentoDTO = agendamentoMapper.toDto(agendamento);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAgendamentoMockMvc.perform(put("/api/agendamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agendamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Agendamento in the database
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAgendamento() throws Exception {
        // Initialize the database
        agendamentoRepository.saveAndFlush(agendamento);
        int databaseSizeBeforeDelete = agendamentoRepository.findAll().size();

        // Get the agendamento
        restAgendamentoMockMvc.perform(delete("/api/agendamentos/{id}", agendamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Agendamento> agendamentoList = agendamentoRepository.findAll();
        assertThat(agendamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agendamento.class);
        Agendamento agendamento1 = new Agendamento();
        agendamento1.setId(1L);
        Agendamento agendamento2 = new Agendamento();
        agendamento2.setId(agendamento1.getId());
        assertThat(agendamento1).isEqualTo(agendamento2);
        agendamento2.setId(2L);
        assertThat(agendamento1).isNotEqualTo(agendamento2);
        agendamento1.setId(null);
        assertThat(agendamento1).isNotEqualTo(agendamento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgendamentoDTO.class);
        AgendamentoDTO agendamentoDTO1 = new AgendamentoDTO();
        agendamentoDTO1.setId(1L);
        AgendamentoDTO agendamentoDTO2 = new AgendamentoDTO();
        assertThat(agendamentoDTO1).isNotEqualTo(agendamentoDTO2);
        agendamentoDTO2.setId(agendamentoDTO1.getId());
        assertThat(agendamentoDTO1).isEqualTo(agendamentoDTO2);
        agendamentoDTO2.setId(2L);
        assertThat(agendamentoDTO1).isNotEqualTo(agendamentoDTO2);
        agendamentoDTO1.setId(null);
        assertThat(agendamentoDTO1).isNotEqualTo(agendamentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(agendamentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(agendamentoMapper.fromId(null)).isNull();
    }
}
