package com.execicio.biblioteca.service;

import com.execicio.biblioteca.model.Livro;
import com.execicio.biblioteca.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LivroService {

    private final LivroRepository repository;

    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }

    public List<Livro> listarLivros() {
        return repository.findAll();
    }

    public Livro buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Livro salvar(Livro livro) {
        return repository.save(livro);
    }

    public Livro atualizar(Long id, Livro livroAtualizado) {
        Livro livro = buscarPorId(id);

        if (livro != null) {
            livro.setTitulo(livroAtualizado.getTitulo());
            livro.setAutor(livroAtualizado.getAutor());
            return repository.save(livro);
        }

        return null;
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Livro emprestar(Long id) {

        Livro livro = buscarPorId(id);

        if (livro == null) {
            return null;
        }

        if (livro.isEmprestado()) {
            throw new RuntimeException("Livro já está emprestado");
        }

        livro.setEmprestado(true);
        livro.setDataEmprestimo(LocalDate.now());

        return repository.save(livro);
    }

    public Livro devolver(Long id) {

        Livro livro = buscarPorId(id);

        if (livro == null) {
            return null;
        }

        if (!livro.isEmprestado()) {
            throw new RuntimeException("Livro não está emprestado");
        }

        livro.setEmprestado(false);
        livro.setDataEmprestimo(null);

        return repository.save(livro);
    }
}