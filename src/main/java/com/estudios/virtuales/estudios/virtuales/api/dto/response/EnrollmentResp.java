package com.estudios.virtuales.estudios.virtuales.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentResp {
    private Long id;
    private UserEnrollResp user;
    private CourseLessonResp course;
    private LocalDate enrollmentDate;
}