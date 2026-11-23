package com.sinpicarse.service;

import com.sinpicarse.model.Contacto;
import com.sinpicarse.repository.ContactoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactoService {

    private final ContactoRepository mensajeContactoRepository;

    public List<Contacto> listarTodos() {
        return mensajeContactoRepository.findAllByOrderByFechaDesc();
    }

    public Contacto obtenerPorId(Long id) {
        return mensajeContactoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado con ID: " + id));
    }

    public Contacto guardar(Contacto mensaje) {
        if (mensaje.getFecha() == null) {
            mensaje.setFecha(LocalDateTime.now());
        }
        return mensajeContactoRepository.save(mensaje);
    }

    public void eliminar(Long id) {
        mensajeContactoRepository.deleteById(id);
    }
}