package com.publicaciones.publicaciones_service.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class PublicacionResponseDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private String estado;
    private String tipo;
    private Double latitud;
    private Double longitud;
    private Long usuarioId;
    private Timestamp fechaPublicacion;
    private String imagenUrl;
}
