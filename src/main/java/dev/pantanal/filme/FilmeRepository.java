package dev.pantanal.filme;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface FilmeRepository extends CrudRepository<Filme, String> {
    public List<Filme> findAll();

    public List<Filme> findByTituloContainingIgnoreCase(String texto);
}
