package com.publicaciones.publicaciones_service.service;

import com.publicaciones.publicaciones_service.model.Imagen;
import com.publicaciones.publicaciones_service.repository.ImagenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImagenUploadService {

    private final ImagenRepository imagenRepository;
    private final RestTemplate restTemplate;

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.service-key}")
    private String supabaseServiceKey;

    @Value("${supabase.storage.bucket}")
    private String bucket;

    private static final long TAMANIO_MAXIMO_BYTES = 5L * 1024 * 1024; // 5MB

    public Imagen subirImagen(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }
        if (file.getSize() > TAMANIO_MAXIMO_BYTES) {
            throw new IllegalArgumentException("La imagen no puede superar los 5MB");
        }

        String extension = obtenerExtension(file.getOriginalFilename());
        String nombreArchivo = UUID.randomUUID() + extension;

        String uploadUrl = supabaseUrl + "/storage/v1/object/" + bucket + "/" + nombreArchivo;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + supabaseServiceKey);
        headers.setContentType(
                file.getContentType() != null
                        ? MediaType.parseMediaType(file.getContentType())
                        : MediaType.APPLICATION_OCTET_STREAM
        );

        HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                uploadUrl, HttpMethod.POST, requestEntity, String.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Error al subir la imagen a Supabase Storage: " + response.getStatusCode());
        }

        String urlPublica = supabaseUrl + "/storage/v1/object/public/" + bucket + "/" + nombreArchivo;

        Imagen imagen = new Imagen();
        imagen.setUrl(urlPublica);
        return imagenRepository.save(imagen);
    }

    private String obtenerExtension(String nombreOriginal) {
        if (nombreOriginal == null || !nombreOriginal.contains(".")) {
            return "";
        }
        return nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
    }
}