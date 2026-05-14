package com.publicaciones.publicaciones_service.repository;

import com.publicaciones.publicaciones_service.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

    List<Publicacion> findByUsuarioId(Long usuarioId);
}
