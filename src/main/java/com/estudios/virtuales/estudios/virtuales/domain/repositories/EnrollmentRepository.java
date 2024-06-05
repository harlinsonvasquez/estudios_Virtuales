package com.estudios.virtuales.estudios.virtuales.domain.repositories;

import com.estudios.virtuales.estudios.virtuales.domain.entities.Enrollment;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Lesson;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    List<Enrollment> findByUsersId(Long userId);
    List<Enrollment> findByCourseId(Long courseId);
}
