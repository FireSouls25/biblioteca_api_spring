package com.biblioteca.api.repository;

import com.biblioteca.api.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByCategoria(String categoria);
    List<Libro> findByDisponibleTrue();
}
