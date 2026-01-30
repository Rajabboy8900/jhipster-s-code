package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CoursePdfAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CoursePdf;
import com.mycompany.myapp.repository.CoursePdfRepository;
import com.mycompany.myapp.service.dto.CoursePdfDTO;
import com.mycompany.myapp.service.mapper.CoursePdfMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CoursePdfResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CoursePdfResourceIT {

    private static final String DEFAULT_LECTURE_URL = "AAAAAAAAAA";
    private static final String UPDATED_LECTURE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TASKS_URL = "AAAAAAAAAA";
    private static final String UPDATED_TASKS_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TESTS = "AAAAAAAAAA";
    private static final String UPDATED_TESTS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/course-pdfs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CoursePdfRepository coursePdfRepository;

    @Autowired
    private CoursePdfMapper coursePdfMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCoursePdfMockMvc;

    private CoursePdf coursePdf;

    private CoursePdf insertedCoursePdf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoursePdf createEntity() {
        return new CoursePdf().lectureUrl(DEFAULT_LECTURE_URL).tasksUrl(DEFAULT_TASKS_URL).tests(DEFAULT_TESTS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoursePdf createUpdatedEntity() {
        return new CoursePdf().lectureUrl(UPDATED_LECTURE_URL).tasksUrl(UPDATED_TASKS_URL).tests(UPDATED_TESTS);
    }

    @BeforeEach
    void initTest() {
        coursePdf = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCoursePdf != null) {
            coursePdfRepository.delete(insertedCoursePdf);
            insertedCoursePdf = null;
        }
    }

    @Test
    @Transactional
    void createCoursePdf() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CoursePdf
        CoursePdfDTO coursePdfDTO = coursePdfMapper.toDto(coursePdf);
        var returnedCoursePdfDTO = om.readValue(
            restCoursePdfMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(coursePdfDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CoursePdfDTO.class
        );

        // Validate the CoursePdf in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCoursePdf = coursePdfMapper.toEntity(returnedCoursePdfDTO);
        assertCoursePdfUpdatableFieldsEquals(returnedCoursePdf, getPersistedCoursePdf(returnedCoursePdf));

        insertedCoursePdf = returnedCoursePdf;
    }

    @Test
    @Transactional
    void createCoursePdfWithExistingId() throws Exception {
        // Create the CoursePdf with an existing ID
        coursePdf.setId(1L);
        CoursePdfDTO coursePdfDTO = coursePdfMapper.toDto(coursePdf);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoursePdfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(coursePdfDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CoursePdf in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCoursePdfs() throws Exception {
        // Initialize the database
        insertedCoursePdf = coursePdfRepository.saveAndFlush(coursePdf);

        // Get all the coursePdfList
        restCoursePdfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coursePdf.getId().intValue())))
            .andExpect(jsonPath("$.[*].lectureUrl").value(hasItem(DEFAULT_LECTURE_URL)))
            .andExpect(jsonPath("$.[*].tasksUrl").value(hasItem(DEFAULT_TASKS_URL)))
            .andExpect(jsonPath("$.[*].tests").value(hasItem(DEFAULT_TESTS)));
    }

    @Test
    @Transactional
    void getCoursePdf() throws Exception {
        // Initialize the database
        insertedCoursePdf = coursePdfRepository.saveAndFlush(coursePdf);

        // Get the coursePdf
        restCoursePdfMockMvc
            .perform(get(ENTITY_API_URL_ID, coursePdf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(coursePdf.getId().intValue()))
            .andExpect(jsonPath("$.lectureUrl").value(DEFAULT_LECTURE_URL))
            .andExpect(jsonPath("$.tasksUrl").value(DEFAULT_TASKS_URL))
            .andExpect(jsonPath("$.tests").value(DEFAULT_TESTS));
    }

    @Test
    @Transactional
    void getNonExistingCoursePdf() throws Exception {
        // Get the coursePdf
        restCoursePdfMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCoursePdf() throws Exception {
        // Initialize the database
        insertedCoursePdf = coursePdfRepository.saveAndFlush(coursePdf);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the coursePdf
        CoursePdf updatedCoursePdf = coursePdfRepository.findById(coursePdf.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCoursePdf are not directly saved in db
        em.detach(updatedCoursePdf);
        updatedCoursePdf.lectureUrl(UPDATED_LECTURE_URL).tasksUrl(UPDATED_TASKS_URL).tests(UPDATED_TESTS);
        CoursePdfDTO coursePdfDTO = coursePdfMapper.toDto(updatedCoursePdf);

        restCoursePdfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, coursePdfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(coursePdfDTO))
            )
            .andExpect(status().isOk());

        // Validate the CoursePdf in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCoursePdfToMatchAllProperties(updatedCoursePdf);
    }

    @Test
    @Transactional
    void putNonExistingCoursePdf() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        coursePdf.setId(longCount.incrementAndGet());

        // Create the CoursePdf
        CoursePdfDTO coursePdfDTO = coursePdfMapper.toDto(coursePdf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoursePdfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, coursePdfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(coursePdfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoursePdf in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCoursePdf() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        coursePdf.setId(longCount.incrementAndGet());

        // Create the CoursePdf
        CoursePdfDTO coursePdfDTO = coursePdfMapper.toDto(coursePdf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoursePdfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(coursePdfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoursePdf in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCoursePdf() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        coursePdf.setId(longCount.incrementAndGet());

        // Create the CoursePdf
        CoursePdfDTO coursePdfDTO = coursePdfMapper.toDto(coursePdf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoursePdfMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(coursePdfDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CoursePdf in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCoursePdfWithPatch() throws Exception {
        // Initialize the database
        insertedCoursePdf = coursePdfRepository.saveAndFlush(coursePdf);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the coursePdf using partial update
        CoursePdf partialUpdatedCoursePdf = new CoursePdf();
        partialUpdatedCoursePdf.setId(coursePdf.getId());

        partialUpdatedCoursePdf.lectureUrl(UPDATED_LECTURE_URL).tasksUrl(UPDATED_TASKS_URL).tests(UPDATED_TESTS);

        restCoursePdfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoursePdf.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCoursePdf))
            )
            .andExpect(status().isOk());

        // Validate the CoursePdf in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCoursePdfUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCoursePdf, coursePdf),
            getPersistedCoursePdf(coursePdf)
        );
    }

    @Test
    @Transactional
    void fullUpdateCoursePdfWithPatch() throws Exception {
        // Initialize the database
        insertedCoursePdf = coursePdfRepository.saveAndFlush(coursePdf);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the coursePdf using partial update
        CoursePdf partialUpdatedCoursePdf = new CoursePdf();
        partialUpdatedCoursePdf.setId(coursePdf.getId());

        partialUpdatedCoursePdf.lectureUrl(UPDATED_LECTURE_URL).tasksUrl(UPDATED_TASKS_URL).tests(UPDATED_TESTS);

        restCoursePdfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoursePdf.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCoursePdf))
            )
            .andExpect(status().isOk());

        // Validate the CoursePdf in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCoursePdfUpdatableFieldsEquals(partialUpdatedCoursePdf, getPersistedCoursePdf(partialUpdatedCoursePdf));
    }

    @Test
    @Transactional
    void patchNonExistingCoursePdf() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        coursePdf.setId(longCount.incrementAndGet());

        // Create the CoursePdf
        CoursePdfDTO coursePdfDTO = coursePdfMapper.toDto(coursePdf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoursePdfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, coursePdfDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(coursePdfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoursePdf in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCoursePdf() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        coursePdf.setId(longCount.incrementAndGet());

        // Create the CoursePdf
        CoursePdfDTO coursePdfDTO = coursePdfMapper.toDto(coursePdf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoursePdfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(coursePdfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoursePdf in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCoursePdf() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        coursePdf.setId(longCount.incrementAndGet());

        // Create the CoursePdf
        CoursePdfDTO coursePdfDTO = coursePdfMapper.toDto(coursePdf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoursePdfMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(coursePdfDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CoursePdf in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCoursePdf() throws Exception {
        // Initialize the database
        insertedCoursePdf = coursePdfRepository.saveAndFlush(coursePdf);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the coursePdf
        restCoursePdfMockMvc
            .perform(delete(ENTITY_API_URL_ID, coursePdf.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return coursePdfRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected CoursePdf getPersistedCoursePdf(CoursePdf coursePdf) {
        return coursePdfRepository.findById(coursePdf.getId()).orElseThrow();
    }

    protected void assertPersistedCoursePdfToMatchAllProperties(CoursePdf expectedCoursePdf) {
        assertCoursePdfAllPropertiesEquals(expectedCoursePdf, getPersistedCoursePdf(expectedCoursePdf));
    }

    protected void assertPersistedCoursePdfToMatchUpdatableProperties(CoursePdf expectedCoursePdf) {
        assertCoursePdfAllUpdatablePropertiesEquals(expectedCoursePdf, getPersistedCoursePdf(expectedCoursePdf));
    }
}
