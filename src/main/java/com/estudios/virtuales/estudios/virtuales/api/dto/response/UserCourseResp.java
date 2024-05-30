package com.estudios.virtuales.estudios.virtuales.api.dto.response;

import com.estudios.virtuales.estudios.virtuales.utils.enums.Role;

import java.util.List;

public class UserCourseResp {
    private Long id;
    private  String userName;
    private String email;
    private String fullName;
    private Role role;
    private List<CourseBasicResp> courses;

}
