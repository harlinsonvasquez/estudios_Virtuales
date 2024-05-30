package com.estudios.virtuales.estudios.virtuales.domain.repositories;

import com.estudios.virtuales.estudios.virtuales.domain.entities.Course;
import com.estudios.virtuales.estudios.virtuales.domain.entities.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
}
