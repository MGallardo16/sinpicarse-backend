package com.sinpicarse.service;

import com.sinpicarse.model.Reportes;
import com.sinpicarse.repository.ReportesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportesService {

    private final ReportesRepository reporteRepository;

    public List<Reportes> listarTodos() {
        return reporteRepository.findAllByOrderByFechaDesc();
    }

    public Reportes obtenerPorId(Long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con ID: " + id));
    }

    public Reportes crear(Reportes reporte) {
        if (reporte.getFecha() == null) {
            reporte.setFecha(LocalDateTime.now());
        }
        return reporteRepository.save(reporte);
    }

    public void eliminar(Long id) {
        reporteRepository.deleteById(id);
    }
}