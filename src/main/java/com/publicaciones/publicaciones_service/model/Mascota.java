package com.publicaciones.publicaciones_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Mascotas")
@Entity
public class Mascota {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
