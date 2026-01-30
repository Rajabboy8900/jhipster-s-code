package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Enrollment;
import com.mycompany.myapp.service.EnrollmentService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EnrollmentResource {

    private final EnrollmentService enrollmentService;

    public EnrollmentResource(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/enrollments")
    public ResponseEntity<Enrollment> enroll(@RequestBody Enrollment enrollment) throws URISyntaxException {
        Enrollment result = enrollmentService.enroll(enrollment);
        return ResponseEntity.created(new URI("/api/enrollments/" + result.getId())).body(result);
    }

    @GetMapping("/users/{userId}/courses")
    public List<Enrollment> getUserCourses(@PathVariable Long userId) {
        return enrollmentService.findByUser(userId);
    }

    @GetMapping("/courses/{courseId}/students")
    public List<Enrollment> getCourseStudents(@PathVariable Long courseId) {
        return enrollmentService.findByCourse(courseId);
    }

    @PutMapping("/enrollments/{id}/progress")
    public Enrollment updateProgress(@PathVariable Long id, @RequestParam Integer progress) {
        return enrollmentService.updateProgress(id, progress);
    }
}
