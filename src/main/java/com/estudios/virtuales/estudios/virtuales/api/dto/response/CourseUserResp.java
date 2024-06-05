package com.estudios.virtuales.estudios.virtuales.api.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CourseUserResp  extends CourseBasic{

    private List<UserBasic> users;

}
