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
public class SubmissionResp {
    private Long id;
    private String content;
    private LocalDate submissionDate;
    private double grade;
    private UserBasicResp user;
    private TaskResp task;

}
