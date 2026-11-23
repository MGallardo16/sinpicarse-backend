package com.sinpicarse.repository;

import com.sinpicarse.model.Blogs;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BlogsRepository extends JpaRepository<Blogs, Long> {
    List<Blogs> findAllByOrderByFechaDesc();
}