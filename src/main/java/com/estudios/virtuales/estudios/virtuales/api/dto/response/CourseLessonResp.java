package com.estudios.virtuales.estudios.virtuales.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseLessonResp {
    private Long id;
    private String courseName;
    private String description;
    private UserEnrollResp instructor;
    private List<LessonBasicResp> lessons;
}
