package com.estudios.virtuales.estudios.virtuales.api.dto.request;

import com.estudios.virtuales.estudios.virtuales.utils.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserReq {
    @NotBlank(message = "el usuario es requerido")
    @Size(min = 1,max = 50)
    private  String userName;
    @NotBlank(message = "el password es requerido")
    @Size(min = 5,max = 250)
    private String password;
    @Email()
    private String email;
    @NotBlank(message = "el nombre y apellido es requerido")
    @Size(min = 10,max = 250)
    private String fullName;
    @NotNull(message = "el rol es requerido")
    private Role role;

}
