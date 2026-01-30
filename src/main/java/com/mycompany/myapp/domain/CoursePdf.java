package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "course_pdf")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CoursePdf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 500)
    @Column(name = "lecture_url", length = 500)
    private String lectureUrl;

    @Size(max = 500)
    @Column(name = "tasks_url", length = 500)
    private String tasksUrl;

    @Lob
    @Column(name = "tests")
    private String tests;

    @JsonIgnoreProperties(value = { "pdf" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "pdf")
    private Course course;

    public Long getId() {
        return this.id;
    }

    public CoursePdf id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLectureUrl() {
        return this.lectureUrl;
    }

    public CoursePdf lectureUrl(String lectureUrl) {
        this.setLectureUrl(lectureUrl);
        return this;
    }

    public void setLectureUrl(String lectureUrl) {
        this.lectureUrl = lectureUrl;
    }

    public String getTasksUrl() {
        return this.tasksUrl;
    }

    public CoursePdf tasksUrl(String tasksUrl) {
        this.setTasksUrl(tasksUrl);
        return this;
    }

    public void setTasksUrl(String tasksUrl) {
        this.tasksUrl = tasksUrl;
    }

    public String getTests() {
        return this.tests;
    }

    public CoursePdf tests(String tests) {
        this.setTests(tests);
        return this;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        if (this.course != null) {
            this.course.setPdf(null);
        }
        if (course != null) {
            course.setPdf(this);
        }
        this.course = course;
    }

    public CoursePdf course(Course course) {
        this.setCourse(course);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoursePdf)) {
            return false;
        }
        return getId() != null && getId().equals(((CoursePdf) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "CoursePdf{" +
            "id=" +
            getId() +
            ", lectureUrl='" +
            getLectureUrl() +
            "'" +
            ", tasksUrl='" +
            getTasksUrl() +
            "'" +
            ", tests='" +
            getTests() +
            "'" +
            "}"
        );
    }
}
