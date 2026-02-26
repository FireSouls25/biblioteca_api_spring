package com.biblioteca.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String autor;
    private LocalDate fechaPublicacion;
    private String categoria;
    private boolean disponible;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario prestadoA;

    public Libro() {}

    public Libro(String titulo, String autor, LocalDate fechaPublicacion, String categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.fechaPublicacion = fechaPublicacion;
        this.categoria = categoria;
        this.disponible = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public Usuario getPrestadoA() { return prestadoA; }
    public void setPrestadoA(Usuario prestadoA) { this.prestadoA = prestadoA; }
}
