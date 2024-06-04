package com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.LessonReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.LessonBasicResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.LessonResp;

public interface ILessonService  extends CrudService<LessonReq, LessonBasicResp,Long>{
    public String FIELD_BY_SORT = "id";
    LessonResp getWithTasks(Long id);
}
