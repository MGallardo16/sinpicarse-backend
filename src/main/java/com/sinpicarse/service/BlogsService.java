package com.sinpicarse.service;

import com.sinpicarse.model.Blogs;
import com.sinpicarse.repository.BlogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogsService {

    private final BlogsRepository blogRepository;

    public List<Blogs> listarTodos() {
        return blogRepository.findAllByOrderByFechaDesc();
    }

    public Blogs obtenerPorId(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog no encontrado con ID: " + id));
    }

    public Blogs crear(Blogs blog) {
        if (blog.getFecha() == null) {
            blog.setFecha(LocalDateTime.now());
        }
        return blogRepository.save(blog);
    }

    public Blogs actualizar(Long id, Blogs blogActualizado) {
        Blogs blog = obtenerPorId(id);
        blog.setTitulo(blogActualizado.getTitulo());
        blog.setContenido(blogActualizado.getContenido());
        blog.setImagen(blogActualizado.getImagen());
        blog.setResumen(blogActualizado.getResumen());
        if (blogActualizado.getFecha() != null) {
            blog.setFecha(blogActualizado.getFecha());
        }
        return blogRepository.save(blog);
    }

    public void eliminar(Long id) {
        blogRepository.deleteById(id);
    }
}