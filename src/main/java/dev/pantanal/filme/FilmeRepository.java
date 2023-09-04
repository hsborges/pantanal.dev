package dev.pantanal.filme;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

public interface FilmeRepository extends ListCrudRepository<Filme, String> {
    public List<Filme> findByTituloContainingIgnoreCase(String texto);
}
