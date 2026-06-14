package com.publicaciones.publicaciones_service.dto;

import com.publicaciones.publicaciones_service.dto.MascotaDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PublicacionRequestDTO {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotNull(message = "La latitud es obligatoria")
    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double latitud;

    @NotNull(message = "La longitud es obligatoria")
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double longitud;

    @NotNull(message = "El usuario es obligatorio")
    private Long usuarioId;

    private Long imagenId;

    @NotNull(message = "La mascota es obligatoria")
    @Valid
    private Mascota DTO mascota;
}
