package com.estudios.virtuales.estudios.virtuales.domain.repositories;

import com.estudios.virtuales.estudios.virtuales.domain.entities.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission,Long> {
    //Collection<Object> findByTasksId(Long id);
    List<Submission> findByTasksId(Long taskId);
    List<Submission> findByUser_Id(Long userId);
}
