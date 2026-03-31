package com.execicio.biblioteca.controller;

import com.execicio.biblioteca.model.Livro;
import com.execicio.biblioteca.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService service;
    public LivroController(LivroService service) {
        this.service = service;
    }

    @GetMapping("/teste")
    public ResponseEntity<String> teste() {
        return ResponseEntity.ok("API Biblioteca funcionando!");
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listar() {
        return ResponseEntity.ok(service.listarLivros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscar(@PathVariable Long id) {
        Livro livro = service.buscarPorId(id);
        
        if (livro == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(livro);
    }

    @PostMapping
    public ResponseEntity<Livro> criar(@RequestBody Livro livro) {
        Livro novoLivro = service.salvar(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoLivro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @RequestBody Livro livro) {
        Livro livroAtualizado = service.atualizar(id, livro);

        if (livroAtualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(livroAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        Livro livro = service.buscarPorId(id);

        if (livro == null) {
            return ResponseEntity.notFound().build();
        }

        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/emprestar")
    public ResponseEntity<?> emprestar(@PathVariable Long id) {
        try {
            Livro livro = service.emprestar(id);

            if (livro == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(livro);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<?> devolver(@PathVariable Long id) {
        try {
            Livro livro = service.devolver(id);

            if (livro == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(livro);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}