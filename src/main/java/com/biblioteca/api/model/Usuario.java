package com.biblioteca.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String direccion;
    
    @OneToOne(mappedBy = "prestadoA")
    private Libro libroPrestado;

    public Usuario() {}

    public Usuario(String nombre, String email, String direccion) {
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public Libro getLibroPrestado() { return libroPrestado; }
    public void setLibroPrestado(Libro libroPrestado) { this.libroPrestado = libroPrestado; }
}
