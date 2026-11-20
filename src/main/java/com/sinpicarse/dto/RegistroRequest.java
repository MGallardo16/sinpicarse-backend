package com.sinpicarse.dto;

import com.sinpicarse.model.Rol;
import lombok.Data;

@Data
public class RegistroRequest {
    private String nombre;
    private String correo;
    private String password;
    private Rol rol; // Opcional (si es null, se asigna CLIENTE)
}