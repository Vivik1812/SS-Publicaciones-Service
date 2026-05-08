package com.publicaciones.publicaciones_service.repository;

import org.springframework.stereotype.Repository;
import com.publicaciones.publicaciones_service.model.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {
}
