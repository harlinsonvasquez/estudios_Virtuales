package com.estudios.virtuales.estudios.virtuales.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "tasks")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Task {//tarea o actividad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String taskTitle;
    @Lob
    private String description;
    @Column(nullable = false)
    private LocalDate dueDate;//fecha de vencimiento

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lesson lessons;

    @OneToMany(mappedBy = "tasks",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Submission>submissions;
}
