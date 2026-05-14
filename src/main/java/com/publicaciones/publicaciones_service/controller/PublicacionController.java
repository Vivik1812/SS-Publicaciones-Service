package com.publicaciones.publicaciones_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.publicaciones.publicaciones_service.dto.PublicacionRequestDTO;
import com.publicaciones.publicaciones_service.dto.PublicacionResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import com.publicaciones.publicaciones_service.service.PublicacionService;

@RestController
@RequestMapping("/api/v1/publicaciones")
@RequiredArgsConstructor
public class PublicacionController {

    private final PublicacionService publicacionService;

    //Crear publicacion
    @PostMapping
    public ResponseEntity<PublicacionResponseDTO> crear(@RequestBody @Valid PublicacionRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publicacionService.crear(dto));
    }

    //Listar todas las publicaciones
    @GetMapping
    public ResponseEntity<List<PublicacionResponseDTO>> listar() {
        return ResponseEntity.ok(publicacionService.listarTodas());
    }

    //obtener publicacion por id
    @GetMapping("/{id}")
    public ResponseEntity<PublicacionResponseDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(publicacionService.obtenerPorId(id));
    }

    //obtener publicacion por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PublicacionResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId){
        return ResponseEntity.ok(publicacionService.listaPorUsuario(usuarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicacionResponseDTO> actualizar(
        @PathVariable Long id,
        @Valid @RequestBody PublicacionRequestDTO dto
    ){
        return ResponseEntity.ok(publicacionService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        publicacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
