package com.mycompany.myapp.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CoursePdf} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CoursePdfDTO implements Serializable {

    private Long id;

    @Size(max = 500)
    private String lectureUrl;

    @Size(max = 500)
    private String tasksUrl;

    @Lob
    private String tests;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLectureUrl() {
        return lectureUrl;
    }

    public void setLectureUrl(String lectureUrl) {
        this.lectureUrl = lectureUrl;
    }

    public String getTasksUrl() {
        return tasksUrl;
    }

    public void setTasksUrl(String tasksUrl) {
        this.tasksUrl = tasksUrl;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoursePdfDTO)) {
            return false;
        }

        CoursePdfDTO coursePdfDTO = (CoursePdfDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, coursePdfDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CoursePdfDTO{" +
            "id=" + getId() +
            ", lectureUrl='" + getLectureUrl() + "'" +
            ", tasksUrl='" + getTasksUrl() + "'" +
            ", tests='" + getTests() + "'" +
            "}";
    }
}
