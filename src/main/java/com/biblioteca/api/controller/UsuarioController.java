package com.biblioteca.api.controller;

import com.biblioteca.api.dto.LibroDTO;
import com.biblioteca.api.dto.UsuarioDTO;
import com.biblioteca.api.model.Libro;
import com.biblioteca.api.model.Usuario;
import com.biblioteca.api.repository.LibroRepository;
import com.biblioteca.api.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(LibroRepository libroRepository, UsuarioRepository usuarioRepository) {
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/{id}/obtener-libro/{libroId}")
    public ResponseEntity<LibroDTO> obtenerLibro(@PathVariable Long id, @PathVariable Long libroId) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        Libro libro = libroRepository.findById(libroId).orElse(null);
        
        if (usuario == null || libro == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!libro.isDisponible()) {
            return ResponseEntity.status(409).build();
        }
        
        libro.setDisponible(false);
        libro.setPrestadoA(usuario);
        libro = libroRepository.save(libro);
        
        return ResponseEntity.ok(toLibroDTO(libro));
    }

    @PostMapping("/{id}/devolver-libro/{libroId}")
    public ResponseEntity<LibroDTO> devolverLibro(@PathVariable Long id, @PathVariable Long libroId) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        Libro libro = libroRepository.findById(libroId).orElse(null);
        
        if (usuario == null || libro == null) {
            return ResponseEntity.badRequest().build();
        }
        if (libro.getPrestadoA() == null || !libro.getPrestadoA().getId().equals(id)) {
            return ResponseEntity.status(403).build();
        }
        
        libro.setDisponible(true);
        libro.setPrestadoA(null);
        libro = libroRepository.save(libro);
        
        return ResponseEntity.ok(toLibroDTO(libro));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id)
            .map(u -> ResponseEntity.ok(toUsuarioDTO(u)))
            .orElse(ResponseEntity.notFound().build());
    }

    private LibroDTO toLibroDTO(Libro libro) {
        LibroDTO dto = new LibroDTO();
        dto.setId(libro.getId());
        dto.setTitulo(libro.getTitulo());
        dto.setAutor(libro.getAutor());
        dto.setFechaPublicacion(libro.getFechaPublicacion());
        dto.setCategoria(libro.getCategoria());
        dto.setDisponible(libro.isDisponible());
        if (libro.getPrestadoA() != null) {
            dto.setPrestadoAId(libro.getPrestadoA().getId());
        }
        return dto;
    }

    private UsuarioDTO toUsuarioDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setDireccion(usuario.getDireccion());
        if (usuario.getLibroPrestado() != null) {
            dto.setLibroPrestadoId(usuario.getLibroPrestado().getId());
        }
        return dto;
    }
}
