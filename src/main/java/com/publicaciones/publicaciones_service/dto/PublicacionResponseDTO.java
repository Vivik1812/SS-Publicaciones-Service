package com.publicaciones.publicaciones_service.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

import com.publicaciones.publicaciones_service.model.Mascota;

@Data
public class PublicacionResponseDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private String estado;
    private Double latitud;
    private Double longitud;
    private Long usuarioId;
    private LocalDateTime fechaPublicacion;
    private List<String> imagenIds;
    private Mascota mascota;
}
