package com.estudios.virtuales.estudios.virtuales.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserBasic {
    private Long id;
    private String email;
    private String fullName;
}
