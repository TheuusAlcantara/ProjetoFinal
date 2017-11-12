package br.uniceub.saafa.web.rest;

import br.uniceub.saafa.SaafaApp;

import br.uniceub.saafa.domain.UBS;
import br.uniceub.saafa.repository.UBSRepository;
import br.uniceub.saafa.service.UBSService;
import br.uniceub.saafa.service.dto.UBSDTO;
import br.uniceub.saafa.service.mapper.UBSMapper;
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
 * Test class for the UBSResource REST controller.
 *
 * @see UBSResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaafaApp.class)
public class UBSResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_HORA_ABERTURA = "AAAAAAAAAA";
    private static final String UPDATED_HORA_ABERTURA = "BBBBBBBBBB";

    private static final String DEFAULT_HORA_FECHAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_HORA_FECHAMENTO = "BBBBBBBBBB";

    @Autowired
    private UBSRepository uBSRepository;

    @Autowired
    private UBSMapper uBSMapper;

    @Autowired
    private UBSService uBSService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUBSMockMvc;

    private UBS uBS;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UBSResource uBSResource = new UBSResource(uBSService);
        this.restUBSMockMvc = MockMvcBuilders.standaloneSetup(uBSResource)
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
    public static UBS createEntity(EntityManager em) {
        UBS uBS = new UBS()
            .nome(DEFAULT_NOME)
            .horaAbertura(DEFAULT_HORA_ABERTURA)
            .horaFechamento(DEFAULT_HORA_FECHAMENTO);
        return uBS;
    }

    @Before
    public void initTest() {
        uBS = createEntity(em);
    }

    @Test
    @Transactional
    public void createUBS() throws Exception {
        int databaseSizeBeforeCreate = uBSRepository.findAll().size();

        // Create the UBS
        UBSDTO uBSDTO = uBSMapper.toDto(uBS);
        restUBSMockMvc.perform(post("/api/u-bs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uBSDTO)))
            .andExpect(status().isCreated());

        // Validate the UBS in the database
        List<UBS> uBSList = uBSRepository.findAll();
        assertThat(uBSList).hasSize(databaseSizeBeforeCreate + 1);
        UBS testUBS = uBSList.get(uBSList.size() - 1);
        assertThat(testUBS.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testUBS.getHoraAbertura()).isEqualTo(DEFAULT_HORA_ABERTURA);
        assertThat(testUBS.getHoraFechamento()).isEqualTo(DEFAULT_HORA_FECHAMENTO);
    }

    @Test
    @Transactional
    public void createUBSWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uBSRepository.findAll().size();

        // Create the UBS with an existing ID
        uBS.setId(1L);
        UBSDTO uBSDTO = uBSMapper.toDto(uBS);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUBSMockMvc.perform(post("/api/u-bs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uBSDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UBS in the database
        List<UBS> uBSList = uBSRepository.findAll();
        assertThat(uBSList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = uBSRepository.findAll().size();
        // set the field null
        uBS.setNome(null);

        // Create the UBS, which fails.
        UBSDTO uBSDTO = uBSMapper.toDto(uBS);

        restUBSMockMvc.perform(post("/api/u-bs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uBSDTO)))
            .andExpect(status().isBadRequest());

        List<UBS> uBSList = uBSRepository.findAll();
        assertThat(uBSList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUBS() throws Exception {
        // Initialize the database
        uBSRepository.saveAndFlush(uBS);

        // Get all the uBSList
        restUBSMockMvc.perform(get("/api/u-bs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uBS.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].horaAbertura").value(hasItem(DEFAULT_HORA_ABERTURA.toString())))
            .andExpect(jsonPath("$.[*].horaFechamento").value(hasItem(DEFAULT_HORA_FECHAMENTO.toString())));
    }

    @Test
    @Transactional
    public void getUBS() throws Exception {
        // Initialize the database
        uBSRepository.saveAndFlush(uBS);

        // Get the uBS
        restUBSMockMvc.perform(get("/api/u-bs/{id}", uBS.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uBS.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.horaAbertura").value(DEFAULT_HORA_ABERTURA.toString()))
            .andExpect(jsonPath("$.horaFechamento").value(DEFAULT_HORA_FECHAMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUBS() throws Exception {
        // Get the uBS
        restUBSMockMvc.perform(get("/api/u-bs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUBS() throws Exception {
        // Initialize the database
        uBSRepository.saveAndFlush(uBS);
        int databaseSizeBeforeUpdate = uBSRepository.findAll().size();

        // Update the uBS
        UBS updatedUBS = uBSRepository.findOne(uBS.getId());
        updatedUBS
            .nome(UPDATED_NOME)
            .horaAbertura(UPDATED_HORA_ABERTURA)
            .horaFechamento(UPDATED_HORA_FECHAMENTO);
        UBSDTO uBSDTO = uBSMapper.toDto(updatedUBS);

        restUBSMockMvc.perform(put("/api/u-bs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uBSDTO)))
            .andExpect(status().isOk());

        // Validate the UBS in the database
        List<UBS> uBSList = uBSRepository.findAll();
        assertThat(uBSList).hasSize(databaseSizeBeforeUpdate);
        UBS testUBS = uBSList.get(uBSList.size() - 1);
        assertThat(testUBS.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testUBS.getHoraAbertura()).isEqualTo(UPDATED_HORA_ABERTURA);
        assertThat(testUBS.getHoraFechamento()).isEqualTo(UPDATED_HORA_FECHAMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingUBS() throws Exception {
        int databaseSizeBeforeUpdate = uBSRepository.findAll().size();

        // Create the UBS
        UBSDTO uBSDTO = uBSMapper.toDto(uBS);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUBSMockMvc.perform(put("/api/u-bs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uBSDTO)))
            .andExpect(status().isCreated());

        // Validate the UBS in the database
        List<UBS> uBSList = uBSRepository.findAll();
        assertThat(uBSList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUBS() throws Exception {
        // Initialize the database
        uBSRepository.saveAndFlush(uBS);
        int databaseSizeBeforeDelete = uBSRepository.findAll().size();

        // Get the uBS
        restUBSMockMvc.perform(delete("/api/u-bs/{id}", uBS.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UBS> uBSList = uBSRepository.findAll();
        assertThat(uBSList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UBS.class);
        UBS uBS1 = new UBS();
        uBS1.setId(1L);
        UBS uBS2 = new UBS();
        uBS2.setId(uBS1.getId());
        assertThat(uBS1).isEqualTo(uBS2);
        uBS2.setId(2L);
        assertThat(uBS1).isNotEqualTo(uBS2);
        uBS1.setId(null);
        assertThat(uBS1).isNotEqualTo(uBS2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UBSDTO.class);
        UBSDTO uBSDTO1 = new UBSDTO();
        uBSDTO1.setId(1L);
        UBSDTO uBSDTO2 = new UBSDTO();
        assertThat(uBSDTO1).isNotEqualTo(uBSDTO2);
        uBSDTO2.setId(uBSDTO1.getId());
        assertThat(uBSDTO1).isEqualTo(uBSDTO2);
        uBSDTO2.setId(2L);
        assertThat(uBSDTO1).isNotEqualTo(uBSDTO2);
        uBSDTO1.setId(null);
        assertThat(uBSDTO1).isNotEqualTo(uBSDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(uBSMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(uBSMapper.fromId(null)).isNull();
    }
}
