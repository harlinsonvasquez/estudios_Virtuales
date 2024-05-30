package com.estudios.virtuales.estudios.virtuales.domain.repositories;

import com.estudios.virtuales.estudios.virtuales.domain.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
}
