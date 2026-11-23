package com.sinpicarse.controller;

import com.sinpicarse.model.Blogs;
import com.sinpicarse.service.BlogsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blogs")
@RequiredArgsConstructor
@Tag(name = "5. Blogs", description = "Gestión y lectura de artículos del blog")
public class BlogsController {

    private final BlogsService blogsService;

    @GetMapping
    @Operation(summary = "Listar todos los blogs ordenados por fecha (Público)")
    public ResponseEntity<List<Blogs>> listar() {
        return ResponseEntity.ok(blogsService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de un blog por ID (Público)")
    public ResponseEntity<Blogs> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(blogsService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo artículo de blog (Solo ADMIN)")
    public ResponseEntity<Blogs> crear(@RequestBody Blogs blog) {
        return ResponseEntity.status(201).body(blogsService.crear(blog));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un artículo de blog existente (Solo ADMIN)")
    public ResponseEntity<Blogs> actualizar(@PathVariable Long id, @RequestBody Blogs blog) {
        return ResponseEntity.ok(blogsService.actualizar(id, blog));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un artículo de blog (Solo ADMIN)")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        blogsService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}