package com.estudios.virtuales.estudios.virtuales.api.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TaskResp extends TaskBasicResp{

    private LessonResp lessonId;
    private List<SubmissionBasicResp> submissions;
}
