package com.estudios.virtuales.estudios.virtuales.api.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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

    private LocalDate enrollmentDate;
}
