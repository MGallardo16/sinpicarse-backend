package com.sinpicarse.controller;

import com.sinpicarse.dto.BoletaRequest;
import com.sinpicarse.model.Boleta;
import com.sinpicarse.service.BoletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/boletas")
@RequiredArgsConstructor
@Tag(name = "Boletas / Órdenes", description = "Generación de compras y consulta de órdenes")
public class BoletaController {

    private final BoletaService boletaService;

    @GetMapping
    @Operation(summary = "Listar todas las boletas (Solo ADMIN y VENDEDOR)")
    public ResponseEntity<List<Boleta>> listarTodas() {
        return ResponseEntity.ok(boletaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de una boleta por ID (Solo ADMIN y VENDEDOR)")
    public ResponseEntity<Boleta> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(boletaService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Generar una nueva boleta de compra y descontar stock (Usuario autenticado)")
    public ResponseEntity<?> crear(@RequestBody BoletaRequest request, Authentication authentication) {
        try {
            String correoUsuario = authentication.getName();
            Boleta boleta = boletaService.generarBoleta(correoUsuario, request);
            return ResponseEntity.status(201).body(boleta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}