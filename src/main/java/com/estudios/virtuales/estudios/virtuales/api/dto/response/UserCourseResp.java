package com.estudios.virtuales.estudios.virtuales.api.dto.response;

import com.estudios.virtuales.estudios.virtuales.utils.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserCourseResp extends  UserBasicResp{

    private List<CourseBasicResp> courses;

}
