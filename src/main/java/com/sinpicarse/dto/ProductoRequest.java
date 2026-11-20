package com.sinpicarse.dto;

import lombok.Data;

@Data
public class ProductoRequest {
    private String nombre;
    private String descripcion;
    private Integer precio;
    private Integer stock;
    private String imagen;
    private Long categoriaId;
}
