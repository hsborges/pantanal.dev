package dev.pantanal.filme;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeRepository repository;

    @GetMapping
    public Collection<Filme> getAllFiles(@RequestParam(value = "titulo", required = false) String texto) {
        if (texto == null)
            return this.repository.findAll();
        return this.repository.findByTituloContainingIgnoreCase(texto);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Filme> getFilme(@PathVariable(value = "id", required = true) String id) {
        var filme = this.repository.findById(id);
        if (filme.isPresent())
            return ResponseEntity.ok(filme.get());
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Filme> createFilme(@RequestBody(required = true) Filme filme) {
        this.repository.save(filme);
        return ResponseEntity.status(HttpStatus.CREATED).body(filme);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteFilme(@PathVariable(value = "id", required = true) String id) {
        var filme = this.repository.findById(id);
        if (filme.isPresent()) {
            this.repository.delete(filme.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
