package com.sinpicarse.dto;

import lombok.Data;
import java.util.List;

@Data
public class BoletaRequest {
    private String direccionEnvio;
    private String telefono;
    private String indicaciones;
    private List<ItemBoletaRequest> items;

    @Data
    public static class ItemBoletaRequest {
        private Long productoId;
        private Integer cantidad;
    }
}
