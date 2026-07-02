package com.publicaciones.publicaciones_service.controller;

import com.publicaciones.publicaciones_service.dto.ImagenResponseDTO;
import com.publicaciones.publicaciones_service.model.Imagen;
import com.publicaciones.publicaciones_service.service.ImagenUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/imagenes")
@RequiredArgsConstructor
public class ImagenController {

    private final ImagenUploadService imagenUploadService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImagenResponseDTO> subir(@RequestParam("file") MultipartFile file) throws IOException {
        Imagen imagen = imagenUploadService.subirImagen(file);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ImagenResponseDTO(imagen.getId(), imagen.getUrl()));
    }
}