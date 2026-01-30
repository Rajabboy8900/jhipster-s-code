package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.CoursePdfAsserts.*;
import static com.mycompany.myapp.domain.CoursePdfTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoursePdfMapperTest {

    private CoursePdfMapper coursePdfMapper;

    @BeforeEach
    void setUp() {
        coursePdfMapper = new CoursePdfMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCoursePdfSample1();
        var actual = coursePdfMapper.toEntity(coursePdfMapper.toDto(expected));
        assertCoursePdfAllPropertiesEquals(expected, actual);
    }
}
