package com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.SubmissionReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.SubmissionResp;

public interface ISubmissionService extends CrudService<SubmissionReq, SubmissionResp,Long>{
    public String FIELD_BY_SORT = "id";
}
