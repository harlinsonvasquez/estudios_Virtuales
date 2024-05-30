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
public class LessonResp  extends LessonBasicResp{
    private CourseBasicResp courseId;
    private List<TaskBasicResp> TasksId;

}
