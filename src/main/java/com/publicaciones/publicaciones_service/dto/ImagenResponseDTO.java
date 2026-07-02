package com.publicaciones.publicaciones_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagenResponseDTO {
    
    private Long id;
    private String url;
};