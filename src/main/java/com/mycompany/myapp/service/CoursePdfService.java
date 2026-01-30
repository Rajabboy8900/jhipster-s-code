package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CoursePdf;
import com.mycompany.myapp.repository.CoursePdfRepository;
import com.mycompany.myapp.service.dto.CoursePdfDTO;
import com.mycompany.myapp.service.mapper.CoursePdfMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.CoursePdf}.
 */
@Service
@Transactional
public class CoursePdfService {

    private static final Logger LOG = LoggerFactory.getLogger(CoursePdfService.class);

    private final CoursePdfRepository coursePdfRepository;

    private final CoursePdfMapper coursePdfMapper;

    public CoursePdfService(CoursePdfRepository coursePdfRepository, CoursePdfMapper coursePdfMapper) {
        this.coursePdfRepository = coursePdfRepository;
        this.coursePdfMapper = coursePdfMapper;
    }

    /**
     * Save a coursePdf.
     *
     * @param coursePdfDTO the entity to save.
     * @return the persisted entity.
     */
    public CoursePdfDTO save(CoursePdfDTO coursePdfDTO) {
        LOG.debug("Request to save CoursePdf : {}", coursePdfDTO);
        CoursePdf coursePdf = coursePdfMapper.toEntity(coursePdfDTO);
        coursePdf = coursePdfRepository.save(coursePdf);
        return coursePdfMapper.toDto(coursePdf);
    }

    /**
     * Update a coursePdf.
     *
     * @param coursePdfDTO the entity to save.
     * @return the persisted entity.
     */
    public CoursePdfDTO update(CoursePdfDTO coursePdfDTO) {
        LOG.debug("Request to update CoursePdf : {}", coursePdfDTO);
        CoursePdf coursePdf = coursePdfMapper.toEntity(coursePdfDTO);
        coursePdf = coursePdfRepository.save(coursePdf);
        return coursePdfMapper.toDto(coursePdf);
    }

    /**
     * Partially update a coursePdf.
     *
     * @param coursePdfDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CoursePdfDTO> partialUpdate(CoursePdfDTO coursePdfDTO) {
        LOG.debug("Request to partially update CoursePdf : {}", coursePdfDTO);

        return coursePdfRepository
            .findById(coursePdfDTO.getId())
            .map(existingCoursePdf -> {
                coursePdfMapper.partialUpdate(existingCoursePdf, coursePdfDTO);

                return existingCoursePdf;
            })
            .map(coursePdfRepository::save)
            .map(coursePdfMapper::toDto);
    }

    /**
     * Get all the coursePdfs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CoursePdfDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CoursePdfs");
        return coursePdfRepository.findAll(pageable).map(coursePdfMapper::toDto);
    }

    /**
     *  Get all the coursePdfs where Course is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CoursePdfDTO> findAllWhereCourseIsNull() {
        LOG.debug("Request to get all coursePdfs where Course is null");
        return StreamSupport.stream(coursePdfRepository.findAll().spliterator(), false)
            .filter(coursePdf -> coursePdf.getCourse() == null)
            .map(coursePdfMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one coursePdf by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CoursePdfDTO> findOne(Long id) {
        LOG.debug("Request to get CoursePdf : {}", id);
        return coursePdfRepository.findById(id).map(coursePdfMapper::toDto);
    }

    /**
     * Delete the coursePdf by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete CoursePdf : {}", id);
        coursePdfRepository.deleteById(id);
    }
}
