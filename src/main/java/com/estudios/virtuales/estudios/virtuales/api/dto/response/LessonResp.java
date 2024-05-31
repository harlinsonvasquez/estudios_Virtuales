package com.estudios.virtuales.estudios.virtuales.api.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class LessonResp  extends LessonBasicResp{
    private CourseBasicResp courseId;
    private List<TaskBasicResp> TasksId;

}
