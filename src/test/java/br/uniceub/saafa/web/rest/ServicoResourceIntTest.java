package br.uniceub.saafa.web.rest;

import br.uniceub.saafa.SaafaApp;

import br.uniceub.saafa.domain.Servico;
import br.uniceub.saafa.repository.ServicoRepository;
import br.uniceub.saafa.service.ServicoService;
import br.uniceub.saafa.service.dto.ServicoDTO;
import br.uniceub.saafa.service.mapper.ServicoMapper;
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
 * Test class for the ServicoResource REST controller.
 *
 * @see ServicoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaafaApp.class)
public class ServicoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ServicoMapper servicoMapper;

    @Autowired
    private ServicoService servicoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServicoMockMvc;

    private Servico servico;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServicoResource servicoResource = new ServicoResource(servicoService);
        this.restServicoMockMvc = MockMvcBuilders.standaloneSetup(servicoResource)
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
    public static Servico createEntity(EntityManager em) {
        Servico servico = new Servico()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO);
        return servico;
    }

    @Before
    public void initTest() {
        servico = createEntity(em);
    }

    @Test
    @Transactional
    public void createServico() throws Exception {
        int databaseSizeBeforeCreate = servicoRepository.findAll().size();

        // Create the Servico
        ServicoDTO servicoDTO = servicoMapper.toDto(servico);
        restServicoMockMvc.perform(post("/api/servicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicoDTO)))
            .andExpect(status().isCreated());

        // Validate the Servico in the database
        List<Servico> servicoList = servicoRepository.findAll();
        assertThat(servicoList).hasSize(databaseSizeBeforeCreate + 1);
        Servico testServico = servicoList.get(servicoList.size() - 1);
        assertThat(testServico.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testServico.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createServicoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = servicoRepository.findAll().size();

        // Create the Servico with an existing ID
        servico.setId(1L);
        ServicoDTO servicoDTO = servicoMapper.toDto(servico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicoMockMvc.perform(post("/api/servicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Servico in the database
        List<Servico> servicoList = servicoRepository.findAll();
        assertThat(servicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = servicoRepository.findAll().size();
        // set the field null
        servico.setNome(null);

        // Create the Servico, which fails.
        ServicoDTO servicoDTO = servicoMapper.toDto(servico);

        restServicoMockMvc.perform(post("/api/servicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicoDTO)))
            .andExpect(status().isBadRequest());

        List<Servico> servicoList = servicoRepository.findAll();
        assertThat(servicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = servicoRepository.findAll().size();
        // set the field null
        servico.setDescricao(null);

        // Create the Servico, which fails.
        ServicoDTO servicoDTO = servicoMapper.toDto(servico);

        restServicoMockMvc.perform(post("/api/servicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicoDTO)))
            .andExpect(status().isBadRequest());

        List<Servico> servicoList = servicoRepository.findAll();
        assertThat(servicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServicos() throws Exception {
        // Initialize the database
        servicoRepository.saveAndFlush(servico);

        // Get all the servicoList
        restServicoMockMvc.perform(get("/api/servicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servico.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    @Test
    @Transactional
    public void getServico() throws Exception {
        // Initialize the database
        servicoRepository.saveAndFlush(servico);

        // Get the servico
        restServicoMockMvc.perform(get("/api/servicos/{id}", servico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(servico.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingServico() throws Exception {
        // Get the servico
        restServicoMockMvc.perform(get("/api/servicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServico() throws Exception {
        // Initialize the database
        servicoRepository.saveAndFlush(servico);
        int databaseSizeBeforeUpdate = servicoRepository.findAll().size();

        // Update the servico
        Servico updatedServico = servicoRepository.findOne(servico.getId());
        updatedServico
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);
        ServicoDTO servicoDTO = servicoMapper.toDto(updatedServico);

        restServicoMockMvc.perform(put("/api/servicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicoDTO)))
            .andExpect(status().isOk());

        // Validate the Servico in the database
        List<Servico> servicoList = servicoRepository.findAll();
        assertThat(servicoList).hasSize(databaseSizeBeforeUpdate);
        Servico testServico = servicoList.get(servicoList.size() - 1);
        assertThat(testServico.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testServico.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingServico() throws Exception {
        int databaseSizeBeforeUpdate = servicoRepository.findAll().size();

        // Create the Servico
        ServicoDTO servicoDTO = servicoMapper.toDto(servico);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restServicoMockMvc.perform(put("/api/servicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(servicoDTO)))
            .andExpect(status().isCreated());

        // Validate the Servico in the database
        List<Servico> servicoList = servicoRepository.findAll();
        assertThat(servicoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteServico() throws Exception {
        // Initialize the database
        servicoRepository.saveAndFlush(servico);
        int databaseSizeBeforeDelete = servicoRepository.findAll().size();

        // Get the servico
        restServicoMockMvc.perform(delete("/api/servicos/{id}", servico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Servico> servicoList = servicoRepository.findAll();
        assertThat(servicoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Servico.class);
        Servico servico1 = new Servico();
        servico1.setId(1L);
        Servico servico2 = new Servico();
        servico2.setId(servico1.getId());
        assertThat(servico1).isEqualTo(servico2);
        servico2.setId(2L);
        assertThat(servico1).isNotEqualTo(servico2);
        servico1.setId(null);
        assertThat(servico1).isNotEqualTo(servico2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicoDTO.class);
        ServicoDTO servicoDTO1 = new ServicoDTO();
        servicoDTO1.setId(1L);
        ServicoDTO servicoDTO2 = new ServicoDTO();
        assertThat(servicoDTO1).isNotEqualTo(servicoDTO2);
        servicoDTO2.setId(servicoDTO1.getId());
        assertThat(servicoDTO1).isEqualTo(servicoDTO2);
        servicoDTO2.setId(2L);
        assertThat(servicoDTO1).isNotEqualTo(servicoDTO2);
        servicoDTO1.setId(null);
        assertThat(servicoDTO1).isNotEqualTo(servicoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(servicoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(servicoMapper.fromId(null)).isNull();
    }
}
