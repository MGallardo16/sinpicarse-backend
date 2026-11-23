package com.sinpicarse.controller;

import com.sinpicarse.model.Contacto;
import com.sinpicarse.service.ContactoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mensajes-contacto")
@RequiredArgsConstructor
@Tag(name = "Mensajes de Contacto", description = "Recepción y gestión de mensajes del formulario de contacto")
public class ContactoController {

    private final ContactoService mensajeContactoService;

    @GetMapping
    @Operation(summary = "Listar todos los mensajes de contacto (Solo ADMIN)")
    public ResponseEntity<List<Contacto>> listar() {
        return ResponseEntity.ok(mensajeContactoService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un mensaje de contacto por ID (Solo ADMIN)")
    public ResponseEntity<Contacto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mensajeContactoService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Enviar un nuevo mensaje de contacto (Público)")
    public ResponseEntity<Contacto> crear(@RequestBody Contacto mensaje) {
        return ResponseEntity.status(201).body(mensajeContactoService.guardar(mensaje));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un mensaje de contacto (Solo ADMIN)")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mensajeContactoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}