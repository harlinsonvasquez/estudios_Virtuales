package com.estudios.virtuales.estudios.virtuales.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CourseLessonResp extends CourseBasicResp {

    private List<LessonBasicResp> lessons;
}
