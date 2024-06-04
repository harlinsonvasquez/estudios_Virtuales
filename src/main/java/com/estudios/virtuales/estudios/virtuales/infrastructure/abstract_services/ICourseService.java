package com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.CourseReq;
;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.CourseBasicResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.CourseLessonResp;

public interface ICourseService extends CrudService< CourseReq, CourseBasicResp,Long> {
    public String FIELD_BY_SORT = "id";
    CourseLessonResp getWithLessons(Long id);
}
