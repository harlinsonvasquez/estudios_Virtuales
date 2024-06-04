package com.estudios.virtuales.estudios.virtuales.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TaskBasic {
    private Long id;
    private String taskTitle;
    private String description;
    private LocalDate dueDate;
}
