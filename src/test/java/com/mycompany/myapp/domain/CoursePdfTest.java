package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CoursePdfTestSamples.*;
import static com.mycompany.myapp.domain.CourseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CoursePdfTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoursePdf.class);
        CoursePdf coursePdf1 = getCoursePdfSample1();
        CoursePdf coursePdf2 = new CoursePdf();
        assertThat(coursePdf1).isNotEqualTo(coursePdf2);

        coursePdf2.setId(coursePdf1.getId());
        assertThat(coursePdf1).isEqualTo(coursePdf2);

        coursePdf2 = getCoursePdfSample2();
        assertThat(coursePdf1).isNotEqualTo(coursePdf2);
    }

    @Test
    void courseTest() {
        CoursePdf coursePdf = getCoursePdfRandomSampleGenerator();
        Course courseBack = getCourseRandomSampleGenerator();

        coursePdf.setCourse(courseBack);
        assertThat(coursePdf.getCourse()).isEqualTo(courseBack);
        assertThat(courseBack.getPdf()).isEqualTo(coursePdf);

        coursePdf.course(null);
        assertThat(coursePdf.getCourse()).isNull();
        assertThat(courseBack.getPdf()).isNull();
    }
}
