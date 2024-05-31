package com.estudios.virtuales.estudios.virtuales.infrastructure.services;

import com.estudios.virtuales.estudios.virtuales.api.dto.request.CourseReq;
import com.estudios.virtuales.estudios.virtuales.api.dto.response.CourseBasicResp;
import com.estudios.virtuales.estudios.virtuales.infrastructure.abstract_services.ICourseService;
import com.estudios.virtuales.estudios.virtuales.utils.enums.SortType;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseService implements ICourseService {
    @Override
    public CourseReq create(CourseBasicResp request) {
        return null;
    }

    @Override
    public CourseReq get(Long aLong) {
        return null;
    }

    @Override
    public CourseReq update(CourseBasicResp request, Long aLong) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public Page<CourseReq> getAll(int page, int size, SortType sort) {
        return null;
    }
}
