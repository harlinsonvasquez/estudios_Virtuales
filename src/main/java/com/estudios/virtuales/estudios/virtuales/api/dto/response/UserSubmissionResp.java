package com.estudios.virtuales.estudios.virtuales.api.dto.response;

import com.estudios.virtuales.estudios.virtuales.utils.enums.Role;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserSubmissionResp extends  UserBasicResp {

    private List<SubmissionBasicResp> submissions;
}
