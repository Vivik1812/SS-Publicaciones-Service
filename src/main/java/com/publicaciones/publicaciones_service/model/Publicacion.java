package com.publicaciones.publicaciones_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Entity
@Table(name = "publicaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descripcion;

    private String estado;

    private Timestamp fechaPublicacion;

    private Double latitud;

    private Double longitud;

    private Long usuarioId;

    private String tipo;

    @ManyToOne
    @JoinColumn(name = "imagen_id")
    private Imagen imagen;
}
