package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Enrollment;
import com.mycompany.myapp.domain.enumeration.EnrollmentStatus;
import com.mycompany.myapp.repository.EnrollmentRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public Enrollment enroll(Enrollment enrollment) {
        if (enrollmentRepository.existsByUserIdAndCourseId(enrollment.getUser().getId(), enrollment.getCourse().getId())) {
            throw new IllegalStateException("User already enrolled to this course");
        }

        enrollment.setEnrollDate(Instant.now());
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        enrollment.setProgress(0);

        return enrollmentRepository.save(enrollment);
    }

    @Transactional(readOnly = true)
    public List<Enrollment> findByUser(Long userId) {
        return enrollmentRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Enrollment> findByCourse(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    public Enrollment updateProgress(Long enrollmentId, Integer progress) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollment.setProgress(progress);

        if (progress >= 100) {
            enrollment.setStatus(EnrollmentStatus.COMPLETED);
        }

        return enrollmentRepository.save(enrollment);
    }
}
