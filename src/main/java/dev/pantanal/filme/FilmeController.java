package dev.pantanal.filme;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.pantanal.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/filmes")
@Tag(name = "Filmes", description = "API de filmes")
public class FilmeController {

    @Autowired
    private FilmeService service;

    private FilmeDTO convertToDTO(Filme filme) {
        return FilmeDTO.builder()
                .id(filme.getId())
                .titulo(filme.getTitulo())
                .duracao(filme.getDuracao())
                .classificacao(filme.getClassificacao())
                .formato(Objects.toString(filme.getFormato(), null))
                .lancamento(filme.getLancamento())
                .build();
    }

    private Filme convertToEntity(FilmeDTO filme) {
        Byte[] imagem = null;

        if (filme.getImagem() != null)
            imagem = Utils.toObjects(filme.getImagem().getBytes(StandardCharsets.UTF_8));

        return Filme.builder()
                .id(filme.getTitulo().toLowerCase().replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s+", "-"))
                .titulo(filme.getTitulo())
                .duracao(filme.getDuracao())
                .classificacao(filme.getClassificacao())
                .imagem(imagem)
                .formato(Filme.Formato.fromString(filme.getFormato()))
                .lancamento(filme.getLancamento())
                .build();
    }

    @GetMapping
    @Operation(summary = "Lista todos os filmes", description = "Lista todos os filmes cadastrados permitindo filtrar por título")
    @ApiResponse(responseCode = "200", description = "Lista de filmes")
    public Collection<FilmeDTO> getAllFiles(
            @Parameter(description = "Título do filme") @RequestParam(value = "titulo", required = false) String texto) {
        return this.service.findFilmes(texto).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Operation(summary = "Obtém um filme", description = "Obtém um filme pelo seu identificador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filme encontrado"),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado", content = @Content(schema = @Schema(hidden = true))) })
    @GetMapping(path = "/{id}")
    public ResponseEntity<FilmeDTO> getFilme(
            @Parameter(description = "identificador") @PathVariable(value = "id", required = true) String id) {
        var filme = this.service.getFilme(id);
        if (filme.isPresent())
            return ResponseEntity.ok(convertToDTO(filme.get()));
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/{id}/capa")
    public ResponseEntity<byte[]> getCapaFilme(@PathVariable(value = "id", required = true) String id) {
        var filme = this.service.getFilme(id);

        if (filme.isPresent() && filme.get().getImagem() != null) {
            var content = new String(Utils.toPrimitives(filme.get().getImagem()), StandardCharsets.UTF_8);
            var data = Pattern.compile("data:(.*);base64,(.*)").matcher(content);
            if (data.find()) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(new MediaType(data.group(1).split("/")[0], data.group(1).split("/")[1]));
                return new ResponseEntity<byte[]>(
                        Base64.getDecoder().decode(data.group(2).getBytes(StandardCharsets.UTF_8)), headers,
                        HttpStatus.OK);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<FilmeDTO> createFilme(@Valid @RequestBody FilmeDTO filme) {
        try {
            var result = this.service.createFilme(convertToEntity(filme));
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(result));
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteFilme(@PathVariable(value = "id", required = true) String id) {
        var filme = this.service.getFilme(id);
        if (filme.isPresent()) {
            this.service.deleteFilme(filme.get().getId());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
