package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Min(value = 1L)
    @Column(name = "external_id", nullable = false)
    private Long externalId;

    @Min(value = 1)
    @Column(name = "version_number")
    private Integer versionNumber;

    @Size(max = 60)
    @Column(name = "module", length = 60)
    private String module;

    @NotNull
    @Size(min = 3, max = 350)
    @Column(name = "title", length = 350, nullable = false)
    private String title;

    @Size(max = 60)
    @Column(name = "creator", length = 60)
    private String creator;

    @Min(value = 0)
    @Column(name = "uses_count")
    private Integer usesCount;

    @Lob
    @Column(name = "description_html")
    private String descriptionHtml;

    @NotNull
    @Size(min = 3, max = 150)
    @Column(name = "route", length = 150, nullable = false)
    private String route;

    @Size(max = 500)
    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Size(max = 500)
    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @NotNull
    @Column(name = "hidden", nullable = false)
    private Boolean hidden;

    @JsonIgnoreProperties(value = { "course" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private CoursePdf pdf;

    public Long getId() {
        return this.id;
    }

    public Course id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExternalId() {
        return this.externalId;
    }

    public Course externalId(Long externalId) {
        this.setExternalId(externalId);
        return this;
    }

    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    public Integer getVersionNumber() {
        return this.versionNumber;
    }

    public Course versionNumber(Integer versionNumber) {
        this.setVersionNumber(versionNumber);
        return this;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getModule() {
        return this.module;
    }

    public Course module(String module) {
        this.setModule(module);
        return this;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getTitle() {
        return this.title;
    }

    public Course title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return this.creator;
    }

    public Course creator(String creator) {
        this.setCreator(creator);
        return this;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getUsesCount() {
        return this.usesCount;
    }

    public Course usesCount(Integer usesCount) {
        this.setUsesCount(usesCount);
        return this;
    }

    public void setUsesCount(Integer usesCount) {
        this.usesCount = usesCount;
    }

    public String getDescriptionHtml() {
        return this.descriptionHtml;
    }

    public Course descriptionHtml(String descriptionHtml) {
        this.setDescriptionHtml(descriptionHtml);
        return this;
    }

    public void setDescriptionHtml(String descriptionHtml) {
        this.descriptionHtml = descriptionHtml;
    }

    public String getRoute() {
        return this.route;
    }

    public Course route(String route) {
        this.setRoute(route);
        return this;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Course imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public Course avatarUrl(String avatarUrl) {
        this.setAvatarUrl(avatarUrl);
        return this;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Boolean getHidden() {
        return this.hidden;
    }

    public Course hidden(Boolean hidden) {
        this.setHidden(hidden);
        return this;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public CoursePdf getPdf() {
        return this.pdf;
    }

    public void setPdf(CoursePdf coursePdf) {
        this.pdf = coursePdf;
    }

    public Course pdf(CoursePdf coursePdf) {
        this.setPdf(coursePdf);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return getId() != null && getId().equals(((Course) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Course{" +
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
            "}";
    }
}
