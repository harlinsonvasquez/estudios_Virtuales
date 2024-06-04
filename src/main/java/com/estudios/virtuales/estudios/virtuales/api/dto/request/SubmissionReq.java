package com.estudios.virtuales.estudios.virtuales.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionReq {
    @NotBlank(message = "la entrega debe tener un contenido")
    private String content;

    private LocalDate submissionDate;
    @NotNull
    private double grade;
    @NotNull(message = "el id del usuario es requerido")
    private Long userId;
    @NotNull(message = "el id del usuario es requerido")
    private Long taskId;
}
