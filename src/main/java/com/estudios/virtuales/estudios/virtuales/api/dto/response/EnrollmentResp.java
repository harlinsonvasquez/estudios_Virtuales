package com.estudios.virtuales.estudios.virtuales.api.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentResp extends EnrollmentBasicResp{

    private UserBasicResp user;
}
