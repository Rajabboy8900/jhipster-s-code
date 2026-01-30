package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CoursePdfDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoursePdfDTO.class);
        CoursePdfDTO coursePdfDTO1 = new CoursePdfDTO();
        coursePdfDTO1.setId(1L);
        CoursePdfDTO coursePdfDTO2 = new CoursePdfDTO();
        assertThat(coursePdfDTO1).isNotEqualTo(coursePdfDTO2);
        coursePdfDTO2.setId(coursePdfDTO1.getId());
        assertThat(coursePdfDTO1).isEqualTo(coursePdfDTO2);
        coursePdfDTO2.setId(2L);
        assertThat(coursePdfDTO1).isNotEqualTo(coursePdfDTO2);
        coursePdfDTO1.setId(null);
        assertThat(coursePdfDTO1).isNotEqualTo(coursePdfDTO2);
    }
}
