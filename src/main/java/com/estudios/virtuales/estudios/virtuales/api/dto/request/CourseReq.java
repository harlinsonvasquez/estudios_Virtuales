package com.estudios.virtuales.estudios.virtuales.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseReq {
    @NotBlank(message = "el nombre del curso es obligatorio")
    private String courseName;
    @NotBlank(message = "la descripcion es obligatoria")
    private String description;
    @NotNull(message = "el id del instrcutor es obligatorio")
    private Long instructorId;
    private List<Long> enrollmentIds;
}
