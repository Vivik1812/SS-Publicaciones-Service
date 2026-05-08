package com.publicaciones.publicaciones_service.service;

import com.publicaciones.publicaciones_service.config.RabbitMQConfig;
import com.publicaciones.publicaciones_service.dto.PublicacionRequestDTO;
import com.publicaciones.publicaciones_service.dto.PublicacionResponseDTO;
import com.publicaciones.publicaciones_service.model.Imagen;
import com.publicaciones.publicaciones_service.model.Publicacion;
import com.publicaciones.publicaciones_service.repository.ImagenRepository;
import com.publicaciones.publicaciones_service.repository.PublicacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final ImagenRepository imagenRepository;
    private final RabbitTemplate rabbitTemplate;

    // Crear una nueva publicación
    public PublicacionResponseDTO crear(PublicacionRequestDTO dto) {
        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo(dto.getTitulo());
        publicacion.setDescripcion(dto.getDescripcion());
        publicacion.setTipo(dto.getTipo());
        publicacion.setLatitud(dto.getLatitud());
        publicacion.setLongitud(dto.getLongitud());
        publicacion.setUsuarioId(dto.getUsuarioId());
        publicacion.setFechaPublicacion(new Timestamp(System.currentTimeMillis()));
        publicacion.setEstado("ACTIVO");

        // asociar imagen del request
        if (dto.getImagenId() != null) {
            Imagen imagen = imagenRepository.findById(dto.getImagenId())
                    .orElseThrow(() -> new RuntimeException("Imagen no encontrada"));
            publicacion.setImagen(imagen);
        }

        Publicacion guardada = publicacionRepository.save(publicacion);

        // enviar mensaje a RabbitMQ
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.CLAVE_ENRUTAMIENTO,
                guardada);

        return mapearAResponse(guardada);
    }

    // obtener publicacion por ID
    public PublicacionResponseDTO obtenerPorId(Long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
        return mapearAResponse(publicacion);
    }

    // listar todas las publicaciones
    public List<PublicacionResponseDTO> listarTodas() {
        return publicacionRepository.findAll()
                .stream()
                .map(this::mapearAResponse)
                .toList();
    }

    // mapear entidad a DTO
    private PublicacionResponseDTO mapearAResponse(Publicacion publicacion) {
        PublicacionResponseDTO response = new PublicacionResponseDTO();
        response.setId(publicacion.getId());
        response.setTitulo(publicacion.getTitulo());
        response.setDescripcion(publicacion.getDescripcion());
        response.setEstado(publicacion.getEstado());
        response.setTipo(publicacion.getTipo());
        response.setLatitud(publicacion.getLatitud());
        response.setLongitud(publicacion.getLongitud());
        response.setUsuarioId(publicacion.getUsuarioId());
        response.setFechaPublicacion(publicacion.getFechaPublicacion());
        return response;
    }
}
