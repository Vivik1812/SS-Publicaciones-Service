package com.publicaciones.publicaciones_service.service;

import com.publicaciones.publicaciones_service.config.RabbitMQConfig;
import com.publicaciones.publicaciones_service.dto.NotificacionEventoDTO;
import com.publicaciones.publicaciones_service.dto.PublicacionRequestDTO;
import com.publicaciones.publicaciones_service.dto.PublicacionResponseDTO;
import com.publicaciones.publicaciones_service.model.Imagen;
import com.publicaciones.publicaciones_service.model.Publicacion;
import com.publicaciones.publicaciones_service.repository.ImagenRepository;
import com.publicaciones.publicaciones_service.repository.PublicacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.publicaciones.publicaciones_service.model.Mascota;

@Service
@RequiredArgsConstructor
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final ImagenRepository imagenRepository;
    private final RabbitTemplate rabbitTemplate;

    // Crear una nueva publicación
    @Transactional
    public PublicacionResponseDTO crear(PublicacionRequestDTO dto) {
        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo(dto.getTitulo());
        publicacion.setDescripcion(dto.getDescripcion());
        publicacion.setEstado(dto.getEstado());
        publicacion.setLatitud(dto.getLatitud());
        publicacion.setLongitud(dto.getLongitud());
        publicacion.setUsuarioId(dto.getUsuarioId());
        Mascota mascota = new Mascota();
        mascota.setNombreMascota(dto.getMascota().getNombreMascota());
        mascota.setEspecie(dto.getMascota().getEspecie());
        mascota.setRaza(dto.getMascota().getRaza());
        mascota.setColor(dto.getMascota().getColor());
        mascota.setSexo(dto.getMascota().getSexo());
        mascota.setTamanio(dto.getMascota().getTamanio());
        publicacion.setMascota(mascota);

        // asociar imagen 
        asociarImagenes(publicacion, dto.getImagenIds());

        Publicacion guardada = publicacionRepository.save(publicacion);

        // enviar mensaje a RabbitMQ
        NotificacionEventoDTO evento = new NotificacionEventoDTO(
                guardada.getUsuarioId(),
                guardada.getId(),
                "Se ha creado una nueva publicación de mascota");
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.CLAVE_ENRUTAMIENTO,
                evento);

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

    // Listar todas las publicaciones de un usuario
    public List<PublicacionResponseDTO> listaPorUsuario(Long usuarioId) {
        return publicacionRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::mapearAResponse)
                .toList();
    }

    //asociar imagenes a la publicacion
    private void asociarImagenes(Publicacion publicacion, List<Long> imagenIds){
        if (imagenIds == null || imagenIds.isEmpty()) {
            return;
        }
            List<Imagen> imagenes = imagenRepository.findAllById(imagenIds);
            imagenes.forEach(imagen -> imagen.setPublicacion(publicacion));
            publicacion.setImagenes(imagenes);
    }
    
    // mapear entidad a DTO
    private PublicacionResponseDTO mapearAResponse(Publicacion publicacion) {
        PublicacionResponseDTO response = new PublicacionResponseDTO();
        response.setId(publicacion.getId());
        response.setTitulo(publicacion.getTitulo());
        response.setDescripcion(publicacion.getDescripcion());
        response.setEstado(publicacion.getEstado());

        response.setLatitud(publicacion.getLatitud());
        response.setLongitud(publicacion.getLongitud());
        response.setUsuarioId(publicacion.getUsuarioId());
        response.setFechaPublicacion(publicacion.getFechaPublicacion());
        response.setMascota(publicacion.getMascota());

        response.setImagenIds(
            publicacion.getImagenes() == null 
            ? List.of()
            :publicacion.getImagenes().stream().map(Imagen::getUrl).toList()
        );
        return response;
    }

    // Editar publicacion
    @Transactional
    public PublicacionResponseDTO actualizar(Long id, PublicacionRequestDTO dto) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicacion no encontrada"));

        publicacion.setTitulo(dto.getTitulo());
        publicacion.setDescripcion(dto.getDescripcion());
        publicacion.setEstado(dto.getEstado());
        publicacion.setLatitud(dto.getLatitud());
        publicacion.setLongitud(dto.getLongitud());
        publicacion.setUsuarioId(dto.getUsuarioId());
        Mascota mascota = new Mascota();
        mascota.setNombreMascota(dto.getMascota().getNombreMascota());
        mascota.setEspecie(dto.getMascota().getEspecie());
        mascota.setRaza(dto.getMascota().getRaza());
        mascota.setColor(dto.getMascota().getColor());
        mascota.setSexo(dto.getMascota().getSexo());
        mascota.setTamanio(dto.getMascota().getTamanio());
        publicacion.setMascota(mascota);

        if (dto.getImagenIds() != null) {
            publicacion.getImagenes().forEach(imagen -> imagen.setPublicacion(null));
            publicacion.getImagenes().clear();
            asociarImagenes(publicacion, dto.getImagenIds());
        }

        Publicacion actualizada = publicacionRepository.save(publicacion);

        return mapearAResponse(actualizada);
    }

    // Eliminar publicacion
    public void eliminar(Long id) {
        Publicacion publicacion = publicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicacion no encontrada"));

        publicacionRepository.delete(publicacion);
    }
}