package dev.pantanal.filme;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.GregorianCalendar;
import java.util.List;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class FilmeRepositoryTests {

    @Autowired
    private FilmeRepository filmeRepository;

    @Test
    public void saveFilme() {
        Filme filme = new Filme();
        filme.setId("barbie-2023");
        filme.setTitulo("Barbie");
        filme.setDuracao(114);
        filme.setClassificacao(12);
        filme.setFormato(Filme.Formato._2D);
        filme.setLancamento(new GregorianCalendar(2023, 6, 20).getTime());

        filmeRepository.save(filme);

        List<Filme> filmes = filmeRepository.findAll();

        assertTrue(filmes.size() == 1);
        assertTrue(filmes.contains(filme));
    }

    @Test
    public void findFilmePorTitulo() {
        assertTrue(filmeRepository.findAll().isEmpty());

        Filme barbie = new Filme();
        barbie.setId("barbie-2023");
        barbie.setTitulo("Barbie");
        barbie.setDuracao(114);
        barbie.setClassificacao(12);
        barbie.setFormato(Filme.Formato._2D);
        barbie.setLancamento(new GregorianCalendar(2023, 6, 20).getTime());

        filmeRepository.save(barbie);

        // testa busca por titulo inexistente
        assertTrue(filmeRepository.findByTituloContainingIgnoreCase("ken").isEmpty());

        // busca por titulo existente com texto parcial com caixa alta
        List<Filme> filmes = filmeRepository.findByTituloContainingIgnoreCase("BAR");
        assertTrue(filmes.size() == 1);
        assertTrue(filmes.contains(barbie));

        // busca por titulo existente com texto parcial com caixa baixa
        filmes = filmeRepository.findByTituloContainingIgnoreCase("bie");
        assertTrue(filmes.size() == 1);
        assertTrue(filmes.contains(barbie));

        Filme barbarian = new Filme();
        barbarian.setId("barbarian-2022");
        barbarian.setTitulo("Barbarian");
        barbarian.setDuracao(102);
        barbarian.setClassificacao(16);
        barbarian.setFormato(Filme.Formato._2D);
        barbarian.setLancamento(new GregorianCalendar(2022, 8, 9).getTime());

        filmeRepository.save(barbarian);

        // busca por titulo existente com texto parcial com caixa alta
        filmes = filmeRepository.findByTituloContainingIgnoreCase("BAR");
        assertTrue(filmes.size() == 2);
        assertTrue(filmes.contains(barbie));
        assertTrue(filmes.contains(barbarian));
    }

}
