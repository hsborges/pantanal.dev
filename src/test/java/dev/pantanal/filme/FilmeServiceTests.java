package dev.pantanal.filme;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.GregorianCalendar;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class FilmeServiceTests {

    @Mock
    private FilmeRepository mockRepository;

    @InjectMocks
    private FilmeService filmeService;

    @Test
    public void createFilme() {
        Filme barbie = new Filme();
        barbie.setId("barbie-2023");
        barbie.setTitulo("Barbie");
        barbie.setDuracao(114);
        barbie.setClassificacao(12);
        barbie.setFormato(Filme.Formato._2D);
        barbie.setLancamento(new GregorianCalendar(2023, 6, 20).getTime());

        Filme barbarian = new Filme();
        barbarian.setId("barbarian-2022");
        barbarian.setTitulo("Barbarian");
        barbarian.setDuracao(102);
        barbarian.setClassificacao(16);
        barbarian.setFormato(Filme.Formato._2D);
        barbarian.setLancamento(new GregorianCalendar(2022, 8, 9).getTime());

        Mockito.when(mockRepository.existsById(barbie.getId())).thenReturn(true);
        Mockito.when(mockRepository.existsById(barbarian.getId())).thenReturn(false);

        Mockito.when(mockRepository.save(Mockito.any(Filme.class)))
                .thenAnswer(invocation -> invocation.getArgument(0, Filme.class));

        assertThrows(DuplicateKeyException.class, () -> filmeService.createFilme(barbie));
        assertDoesNotThrow(() -> filmeService.createFilme(barbarian));
    }

}
