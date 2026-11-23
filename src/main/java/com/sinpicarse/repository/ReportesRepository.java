package com.sinpicarse.repository;

import com.sinpicarse.model.Reportes;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReportesRepository extends JpaRepository<Reportes, Long> {
    List<Reportes> findAllByOrderByFechaDesc();
}