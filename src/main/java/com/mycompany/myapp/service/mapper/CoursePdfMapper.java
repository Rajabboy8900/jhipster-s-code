package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.CoursePdf;
import com.mycompany.myapp.service.dto.CoursePdfDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CoursePdf} and its DTO {@link CoursePdfDTO}.
 */
@Mapper(componentModel = "spring")
public interface CoursePdfMapper extends EntityMapper<CoursePdfDTO, CoursePdf> {}
