package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CoursePdf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface CoursePdfRepository extends JpaRepository<CoursePdf, Long> {}
