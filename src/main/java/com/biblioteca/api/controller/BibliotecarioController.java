package com.biblioteca.api.controller;

import com.biblioteca.api.dto.LibroDTO;
import com.biblioteca.api.dto.UsuarioDTO;
import com.biblioteca.api.model.Libro;
import com.biblioteca.api.model.Usuario;
import com.biblioteca.api.repository.LibroRepository;
import com.biblioteca.api.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bibliotecario")
public class BibliotecarioController {

    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;

    public BibliotecarioController(LibroRepository libroRepository, UsuarioRepository usuarioRepository) {
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/libros")
    public ResponseEntity<LibroDTO> registrarLibro(@RequestBody LibroDTO dto) {
        Libro libro = new Libro(dto.getTitulo(), dto.getAutor(), dto.getFechaPublicacion(), dto.getCategoria());
        libro = libroRepository.save(libro);
        return ResponseEntity.ok(toLibroDTO(libro));
    }

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioDTO> registrarUsuario(@RequestBody UsuarioDTO dto) {
        Usuario usuario = new Usuario(dto.getNombre(), dto.getEmail(), dto.getDireccion());
        usuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(toUsuarioDTO(usuario));
    }

    @GetMapping("/catalogo/categoria/{categoria}")
    public ResponseEntity<List<LibroDTO>> consultarCategoria(@PathVariable String categoria) {
        List<Libro> libros = libroRepository.findByCategoria(categoria);
        return ResponseEntity.ok(libros.stream().map(this::toLibroDTO).collect(Collectors.toList()));
    }

    @DeleteMapping("/libros/{id}")
    public ResponseEntity<Void> darDeBajaLibro(@PathVariable Long id) {
        if (!libroRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        libroRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> darDeBajaUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/libros/{id}")
    public ResponseEntity<LibroDTO> editarLibro(@PathVariable Long id, @RequestBody LibroDTO dto) {
        return libroRepository.findById(id).map(libro -> {
            libro.setTitulo(dto.getTitulo());
            libro.setAutor(dto.getAutor());
            libro.setFechaPublicacion(dto.getFechaPublicacion());
            libro.setCategoria(dto.getCategoria());
            libro = libroRepository.save(libro);
            return ResponseEntity.ok(toLibroDTO(libro));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> editarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(dto.getNombre());
            usuario.setEmail(dto.getEmail());
            usuario.setDireccion(dto.getDireccion());
            usuario = usuarioRepository.save(usuario);
            return ResponseEntity.ok(toUsuarioDTO(usuario));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/prestamos")
    public ResponseEntity<LibroDTO> prestarLibro(@RequestParam Long libroId, @RequestParam Long usuarioId) {
        Libro libro = libroRepository.findById(libroId).orElse(null);
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        
        if (libro == null || usuario == null) {
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
