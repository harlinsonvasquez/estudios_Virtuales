package com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.EnrollmentReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.EnrollmentBasicResp;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.EnrollmentResp;

public interface IEnrollmentService extends CrudService<EnrollmentReq, EnrollmentResp,Long> {
    public String FIELD_BY_SORT = "id";
}
