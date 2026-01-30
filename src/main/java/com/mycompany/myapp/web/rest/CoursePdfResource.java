package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CoursePdfRepository;
import com.mycompany.myapp.service.CoursePdfService;
import com.mycompany.myapp.service.dto.CoursePdfDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.CoursePdf}.
 */
@RestController
@RequestMapping("/api/course-pdfs")
public class CoursePdfResource {

    private static final Logger LOG = LoggerFactory.getLogger(CoursePdfResource.class);

    private static final String ENTITY_NAME = "coursePdf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoursePdfService coursePdfService;

    private final CoursePdfRepository coursePdfRepository;

    public CoursePdfResource(CoursePdfService coursePdfService, CoursePdfRepository coursePdfRepository) {
        this.coursePdfService = coursePdfService;
        this.coursePdfRepository = coursePdfRepository;
    }

    /**
     * {@code POST  /course-pdfs} : Create a new coursePdf.
     *
     * @param coursePdfDTO the coursePdfDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coursePdfDTO, or with status {@code 400 (Bad Request)} if the coursePdf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CoursePdfDTO> createCoursePdf(@Valid @RequestBody CoursePdfDTO coursePdfDTO) throws URISyntaxException {
        LOG.debug("REST request to save CoursePdf : {}", coursePdfDTO);
        if (coursePdfDTO.getId() != null) {
            throw new BadRequestAlertException("A new coursePdf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        coursePdfDTO = coursePdfService.save(coursePdfDTO);
        return ResponseEntity.created(new URI("/api/course-pdfs/" + coursePdfDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, coursePdfDTO.getId().toString()))
            .body(coursePdfDTO);
    }

    /**
     * {@code PUT  /course-pdfs/:id} : Updates an existing coursePdf.
     *
     * @param id the id of the coursePdfDTO to save.
     * @param coursePdfDTO the coursePdfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coursePdfDTO,
     * or with status {@code 400 (Bad Request)} if the coursePdfDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coursePdfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CoursePdfDTO> updateCoursePdf(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CoursePdfDTO coursePdfDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CoursePdf : {}, {}", id, coursePdfDTO);
        if (coursePdfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coursePdfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coursePdfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        coursePdfDTO = coursePdfService.update(coursePdfDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coursePdfDTO.getId().toString()))
            .body(coursePdfDTO);
    }

    /**
     * {@code PATCH  /course-pdfs/:id} : Partial updates given fields of an existing coursePdf, field will ignore if it is null
     *
     * @param id the id of the coursePdfDTO to save.
     * @param coursePdfDTO the coursePdfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coursePdfDTO,
     * or with status {@code 400 (Bad Request)} if the coursePdfDTO is not valid,
     * or with status {@code 404 (Not Found)} if the coursePdfDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the coursePdfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CoursePdfDTO> partialUpdateCoursePdf(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CoursePdfDTO coursePdfDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CoursePdf partially : {}, {}", id, coursePdfDTO);
        if (coursePdfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coursePdfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coursePdfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CoursePdfDTO> result = coursePdfService.partialUpdate(coursePdfDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coursePdfDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /course-pdfs} : get all the coursePdfs.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coursePdfs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CoursePdfDTO>> getAllCoursePdfs(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("course-is-null".equals(filter)) {
            LOG.debug("REST request to get all CoursePdfs where course is null");
            return new ResponseEntity<>(coursePdfService.findAllWhereCourseIsNull(), HttpStatus.OK);
        }
        LOG.debug("REST request to get a page of CoursePdfs");
        Page<CoursePdfDTO> page = coursePdfService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /course-pdfs/:id} : get the "id" coursePdf.
     *
     * @param id the id of the coursePdfDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coursePdfDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CoursePdfDTO> getCoursePdf(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CoursePdf : {}", id);
        Optional<CoursePdfDTO> coursePdfDTO = coursePdfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coursePdfDTO);
    }

    /**
     * {@code DELETE  /course-pdfs/:id} : delete the "id" coursePdf.
     *
     * @param id the id of the coursePdfDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoursePdf(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CoursePdf : {}", id);
        coursePdfService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
