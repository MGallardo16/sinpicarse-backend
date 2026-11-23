package com.sinpicarse.controller;

import com.sinpicarse.model.Reportes;
import com.sinpicarse.service.ReportesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
@Tag(name = "7. Reportes", description = "Gestión de reportes internos y del sistema")
public class ReportesController {

    private final ReportesService reporteService;

    @GetMapping
    @Operation(summary = "Listar todos los reportes (Solo ADMIN)")
    public ResponseEntity<List<Reportes>> listar() {
        return ResponseEntity.ok(reporteService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un reporte por ID (Solo ADMIN)")
    public ResponseEntity<Reportes> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(reporteService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo reporte (Solo ADMIN / VENDEDOR)")
    public ResponseEntity<Reportes> crear(@RequestBody Reportes reporte) {
        return ResponseEntity.status(201).body(reporteService.crear(reporte));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un reporte (Solo ADMIN)")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}