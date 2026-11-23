package com.sinpicarse.controller;

import com.sinpicarse.dto.RegistroRequest;
import com.sinpicarse.model.Rol;
import com.sinpicarse.model.Usuario;
import com.sinpicarse.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios y Administradores", description = "Gestión de cuentas del sistema (Solo ADMIN)")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Listar todos los usuarios registrados (Solo ADMIN)")
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario o administrador (Solo ADMIN)")
    public ResponseEntity<?> crear(@RequestBody RegistroRequest request) {
        try {
            Usuario creado = usuarioService.crearUsuarioConRol(request, Rol.CLIENTE);
            return ResponseEntity.status(201).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar perfil de usuario (Solo ADMIN)")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody RegistroRequest request) {
        return ResponseEntity.ok(
                usuarioService.actualizarPerfil(id, request.getNombre(), request.getCorreo(), request.getPassword())
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario por ID (Solo ADMIN)")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}