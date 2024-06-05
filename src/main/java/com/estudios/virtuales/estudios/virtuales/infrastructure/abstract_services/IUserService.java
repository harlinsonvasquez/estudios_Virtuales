package com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.UserReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.*;

import java.util.List;

public interface IUserService  extends CrudService<UserReq, UserBasicResp,Long>{
    public String FIELD_BY_SORT = "name";
    UserSubmissionResp getWithSubmissions(Long id);

    List<CourseBasic> getCoursesByUserId(Long userId);
    UserCourseResp getWithCourses(Long id);
}
