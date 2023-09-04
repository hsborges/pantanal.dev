package dev.pantanal.filme;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository repository;

    public List<Filme> findFilmes(String texto) {
        if (texto == null || texto.isEmpty())
            return this.repository.findAll();
        else
            return this.repository.findByTituloContainingIgnoreCase(texto);
    }

    public Optional<Filme> getFilme(String id) {
        return this.repository.findById(id);
    }

    public Filme createFilme(Filme filme) throws DuplicateKeyException {
        if (this.repository.existsById(filme.getId()))
            throw new DuplicateKeyException("Filme já cadastrado");
        return this.repository.save(filme);
    }

    public void deleteFilme(String id) {
        this.repository.deleteById(id);
    }

}
