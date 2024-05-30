package com.estudios.virtuales.estudios.virtuales.api.dto.response;

import com.estudios.virtuales.estudios.virtuales.utils.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSubmissionResp {
    private Long id;
    private  String userName;
    private String email;
    private String fullName;
    private Role role;
    private List<SubmissionBasicResp> submissions;
}
