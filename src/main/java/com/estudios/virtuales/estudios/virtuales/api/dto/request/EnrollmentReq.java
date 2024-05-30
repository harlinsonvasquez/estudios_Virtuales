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
public class EnrollmentReq {
    @NotNull(message = "el id del usuario es requerido")
    private Long userId;
    @NotNull(message = "el id del curso es requerido")
    private Long courseId;
    @NotBlank(message = "la fecha de matrucula es requerida")
    private LocalDate enrollmentDate;
}
