package com.estudios.virtuales.estudios.virtuales.api.dto.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)//ocupar el mismo espacio en memoria que la clase madre
public class ErrorsResp extends BaseErrorResp {
    private List<Map<String,String>> errors;
}
