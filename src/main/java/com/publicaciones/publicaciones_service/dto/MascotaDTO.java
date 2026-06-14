package com.publicaciones.publicaciones_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MascotaDTO {
    @NotBlank 
    @Size(min = 2, max = 50) 
    private String nombreMascota;

    @NotBlank 
    @Size(max = 50)          
    private String especie;
    
    @Size(max = 50)                    
    private String raza;

    @NotBlank 
    @Size(max = 50)          
    private String color;

    @NotBlank 
    @Size(max = 50)          
    private String sexo;

    @NotBlank 
    @Size(max = 50)          
    private String tamanio;
}