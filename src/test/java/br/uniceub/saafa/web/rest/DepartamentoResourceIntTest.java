package br.uniceub.saafa.web.rest;

import br.uniceub.saafa.SaafaApp;

import br.uniceub.saafa.domain.Departamento;
import br.uniceub.saafa.repository.DepartamentoRepository;
import br.uniceub.saafa.service.DepartamentoService;
import br.uniceub.saafa.service.dto.DepartamentoDTO;
import br.uniceub.saafa.service.mapper.DepartamentoMapper;
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
 * Test class for the DepartamentoResource REST controller.
 *
 * @see DepartamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaafaApp.class)
public class DepartamentoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private DepartamentoMapper departamentoMapper;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDepartamentoMockMvc;

    private Departamento departamento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepartamentoResource departamentoResource = new DepartamentoResource(departamentoService);
        this.restDepartamentoMockMvc = MockMvcBuilders.standaloneSetup(departamentoResource)
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
    public static Departamento createEntity(EntityManager em) {
        Departamento departamento = new Departamento()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO);
        return departamento;
    }

    @Before
    public void initTest() {
        departamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartamento() throws Exception {
        int databaseSizeBeforeCreate = departamentoRepository.findAll().size();

        // Create the Departamento
        DepartamentoDTO departamentoDTO = departamentoMapper.toDto(departamento);
        restDepartamentoMockMvc.perform(post("/api/departamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Departamento in the database
        List<Departamento> departamentoList = departamentoRepository.findAll();
        assertThat(departamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Departamento testDepartamento = departamentoList.get(departamentoList.size() - 1);
        assertThat(testDepartamento.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDepartamento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createDepartamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departamentoRepository.findAll().size();

        // Create the Departamento with an existing ID
        departamento.setId(1L);
        DepartamentoDTO departamentoDTO = departamentoMapper.toDto(departamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartamentoMockMvc.perform(post("/api/departamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Departamento in the database
        List<Departamento> departamentoList = departamentoRepository.findAll();
        assertThat(departamentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = departamentoRepository.findAll().size();
        // set the field null
        departamento.setNome(null);

        // Create the Departamento, which fails.
        DepartamentoDTO departamentoDTO = departamentoMapper.toDto(departamento);

        restDepartamentoMockMvc.perform(post("/api/departamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Departamento> departamentoList = departamentoRepository.findAll();
        assertThat(departamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = departamentoRepository.findAll().size();
        // set the field null
        departamento.setDescricao(null);

        // Create the Departamento, which fails.
        DepartamentoDTO departamentoDTO = departamentoMapper.toDto(departamento);

        restDepartamentoMockMvc.perform(post("/api/departamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departamentoDTO)))
            .andExpect(status().isBadRequest());

        List<Departamento> departamentoList = departamentoRepository.findAll();
        assertThat(departamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepartamentos() throws Exception {
        // Initialize the database
        departamentoRepository.saveAndFlush(departamento);

        // Get all the departamentoList
        restDepartamentoMockMvc.perform(get("/api/departamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getDepartamento() throws Exception {
        // Initialize the database
        departamentoRepository.saveAndFlush(departamento);

        // Get the departamento
        restDepartamentoMockMvc.perform(get("/api/departamentos/{id}", departamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(departamento.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDepartamento() throws Exception {
        // Get the departamento
        restDepartamentoMockMvc.perform(get("/api/departamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartamento() throws Exception {
        // Initialize the database
        departamentoRepository.saveAndFlush(departamento);
        int databaseSizeBeforeUpdate = departamentoRepository.findAll().size();

        // Update the departamento
        Departamento updatedDepartamento = departamentoRepository.findOne(departamento.getId());
        updatedDepartamento
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);
        DepartamentoDTO departamentoDTO = departamentoMapper.toDto(updatedDepartamento);

        restDepartamentoMockMvc.perform(put("/api/departamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departamentoDTO)))
            .andExpect(status().isOk());

        // Validate the Departamento in the database
        List<Departamento> departamentoList = departamentoRepository.findAll();
        assertThat(departamentoList).hasSize(databaseSizeBeforeUpdate);
        Departamento testDepartamento = departamentoList.get(departamentoList.size() - 1);
        assertThat(testDepartamento.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDepartamento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingDepartamento() throws Exception {
        int databaseSizeBeforeUpdate = departamentoRepository.findAll().size();

        // Create the Departamento
        DepartamentoDTO departamentoDTO = departamentoMapper.toDto(departamento);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDepartamentoMockMvc.perform(put("/api/departamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Departamento in the database
        List<Departamento> departamentoList = departamentoRepository.findAll();
        assertThat(departamentoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDepartamento() throws Exception {
        // Initialize the database
        departamentoRepository.saveAndFlush(departamento);
        int databaseSizeBeforeDelete = departamentoRepository.findAll().size();

        // Get the departamento
        restDepartamentoMockMvc.perform(delete("/api/departamentos/{id}", departamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Departamento> departamentoList = departamentoRepository.findAll();
        assertThat(departamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Departamento.class);
        Departamento departamento1 = new Departamento();
        departamento1.setId(1L);
        Departamento departamento2 = new Departamento();
        departamento2.setId(departamento1.getId());
        assertThat(departamento1).isEqualTo(departamento2);
        departamento2.setId(2L);
        assertThat(departamento1).isNotEqualTo(departamento2);
        departamento1.setId(null);
        assertThat(departamento1).isNotEqualTo(departamento2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartamentoDTO.class);
        DepartamentoDTO departamentoDTO1 = new DepartamentoDTO();
        departamentoDTO1.setId(1L);
        DepartamentoDTO departamentoDTO2 = new DepartamentoDTO();
        assertThat(departamentoDTO1).isNotEqualTo(departamentoDTO2);
        departamentoDTO2.setId(departamentoDTO1.getId());
        assertThat(departamentoDTO1).isEqualTo(departamentoDTO2);
        departamentoDTO2.setId(2L);
        assertThat(departamentoDTO1).isNotEqualTo(departamentoDTO2);
        departamentoDTO1.setId(null);
        assertThat(departamentoDTO1).isNotEqualTo(departamentoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(departamentoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(departamentoMapper.fromId(null)).isNull();
    }
}
