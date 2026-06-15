package com.publicaciones.publicaciones_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionEventoDTO {
    private Long usuarioId;
    private Long publicacionId;
    private String mensaje;
}