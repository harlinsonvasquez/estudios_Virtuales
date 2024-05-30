package com.estudios.virtuales.estudios.virtuales.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CourseBasicResp {
    private Long id;
    private String courseName;
    private String description;
    private UserBasicResp instructor;
}
