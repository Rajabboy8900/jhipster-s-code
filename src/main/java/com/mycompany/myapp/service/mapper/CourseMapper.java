package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.CoursePdf;
import com.mycompany.myapp.service.dto.CourseDTO;
import com.mycompany.myapp.service.dto.CoursePdfDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Course} and its DTO {@link CourseDTO}.
 */
@Mapper(componentModel = "spring")
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {
    @Mapping(target = "pdf", source = "pdf", qualifiedByName = "coursePdfId")
    CourseDTO toDto(Course s);

    @Named("coursePdfId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CoursePdfDTO toDtoCoursePdfId(CoursePdf coursePdf);
}
