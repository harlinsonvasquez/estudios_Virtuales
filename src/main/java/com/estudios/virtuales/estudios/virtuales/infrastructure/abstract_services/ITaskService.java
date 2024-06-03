package com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.TaskReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.TaskBasicResp;

public interface ITaskService extends CrudService<TaskReq, TaskBasicResp,Long>{
    public String FIELD_BY_SORT = "id";
}
