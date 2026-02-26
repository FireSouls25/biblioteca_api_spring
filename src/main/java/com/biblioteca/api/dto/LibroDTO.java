package com.biblioteca.api.dto;

import java.time.LocalDate;

public class LibroDTO {
    private Long id;
    private String titulo;
    private String autor;
    private LocalDate fechaPublicacion;
    private String categoria;
    private boolean disponible;
    private Long prestadoAId;

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
    public Long getPrestadoAId() { return prestadoAId; }
    public void setPrestadoAId(Long prestadoAId) { this.prestadoAId = prestadoAId; }
}
