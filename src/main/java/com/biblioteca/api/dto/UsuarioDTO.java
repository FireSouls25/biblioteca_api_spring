package com.biblioteca.api.dto;

public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private String direccion;
    private Long libroPrestadoId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public Long getLibroPrestadoId() { return libroPrestadoId; }
    public void setLibroPrestadoId(Long libroPrestadoId) { this.libroPrestadoId = libroPrestadoId; }
}
