package com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.CourseReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.CourseBasicResp;

public interface ICourseService extends CrudService<CourseBasicResp, CourseReq,Long> {
}
