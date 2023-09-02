package dev.pantanal.filme;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class FilmeRepository {

    private Map<String, Filme> filmes = new HashMap<>();
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @PostConstruct
    public void init() throws ParseException {
        this.filmes.put("barbie", new Filme("barbie", "Barbie", 114, Filme.Formato._2D, this.formatter.parse("2023-06-20")));
    }

    public Collection<Filme> findAll() {
        return this.filmes.values();
    }

     
    public Optional<Filme> findById(String id){
        Filme filme = null;
        if (this.filmes.containsKey(id)) filme = this.filmes.get(id);
        return Optional.ofNullable(filme);
    }

    public void addFilme(Filme filme) {
        this.filmes.put(filme.getId(), filme);
    }
    
    public void deleteFilme(Filme filme) {
        this.filmes.remove(filme.getId());
    }
}
