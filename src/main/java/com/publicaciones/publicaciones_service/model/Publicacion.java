package com.publicaciones.publicaciones_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "publicaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String titulo;

    @NotBlank
    @Size(min = 10, max = 1000)
    private String descripcion;

    @NotBlank
    @Size(max = 50)
    private String estado;

    private LocalDateTime fechaPublicacion;

    @NotNull
    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    private Double latitud;

    @NotNull
    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    private Double longitud;

    @NotNull
    private Long usuarioId;

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Imagen> imagenes = new ArrayList<>();
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    @PrePersist
    public void prePersist(){
        this.fechaPublicacion = LocalDateTime.now();
    }
}
