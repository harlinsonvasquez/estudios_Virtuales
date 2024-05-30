package com.estudios.virtuales.estudios.virtuales.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonReq {
    @NotBlank(message = "el titulo es necesario")
    private String lessonTitle;
    private String content;
    @NotNull(message = "el id del curso es obligatoria")
    private Long courseId;

}
