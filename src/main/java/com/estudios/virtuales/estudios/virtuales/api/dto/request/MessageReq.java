package com.estudios.virtuales.estudios.virtuales.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageReq {
    @NotNull(message = "el id del remitente es obligatorio")
    private Long senderId;
    @NotNull(message = "el id del destinatario es obligatorio")
    private Long receiverId;
    @NotNull(message = "el id del curso es obligatorio")
    private Long courseId;
    @NotBlank(message = "el contenido del mensaje es obligatorio")
    private String messageContent;
    @NotBlank(message = "la fecha de envio es necesaria")
    private LocalDate sentDate;
}
