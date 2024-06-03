package com.estudios.virtuales.estudios.virtuales.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskReq {
    @NotBlank(message = "el titulo es requerido")
    @Size(min = 2, max = 100)
    private String taskTitle;
    @NotBlank(message = "la description es requerido")
    private String description;

    private LocalDate dueDate;
   @NotNull(message = "debes suministrar un id valido")
    private Long lessonId;
}
