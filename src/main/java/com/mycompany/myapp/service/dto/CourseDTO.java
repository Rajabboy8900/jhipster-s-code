package com.mycompany.myapp.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Course} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CourseDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 1L)
    private Long externalId;

    @Min(value = 1)
    private Integer versionNumber;

    @Size(max = 60)
    private String module;

    @NotNull
    @Size(min = 3, max = 350)
    private String title;

    @Size(max = 60)
    private String creator;

    @Min(value = 0)
    private Integer usesCount;

    @Lob
    private String descriptionHtml;

    @NotNull
    @Size(min = 3, max = 150)
    private String route;

    @Size(max = 500)
    private String imageUrl;

    @Size(max = 500)
    private String avatarUrl;

    @NotNull
    private Boolean hidden;

    private CoursePdfDTO pdf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExternalId() {
        return externalId;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getUsesCount() {
        return usesCount;
    }

    public void setUsesCount(Integer usesCount) {
        this.usesCount = usesCount;
    }

    public String getDescriptionHtml() {
        return descriptionHtml;
    }

    public void setDescriptionHtml(String descriptionHtml) {
        this.descriptionHtml = descriptionHtml;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public CoursePdfDTO getPdf() {
        return pdf;
    }

    public void setPdf(CoursePdfDTO pdf) {
        this.pdf = pdf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseDTO)) {
            return false;
        }

        CourseDTO courseDTO = (CourseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, courseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseDTO{" +
            "id=" + getId() +
            ", externalId=" + getExternalId() +
            ", versionNumber=" + getVersionNumber() +
            ", module='" + getModule() + "'" +
            ", title='" + getTitle() + "'" +
            ", creator='" + getCreator() + "'" +
            ", usesCount=" + getUsesCount() +
            ", descriptionHtml='" + getDescriptionHtml() + "'" +
            ", route='" + getRoute() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", avatarUrl='" + getAvatarUrl() + "'" +
            ", hidden='" + getHidden() + "'" +
            ", pdf=" + getPdf() +
            "}";
    }
}
