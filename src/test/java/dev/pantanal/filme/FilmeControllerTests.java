package dev.pantanal.filme;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FilmeControllerTests {

    @MockBean
    private FilmeService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void filmeNaoEncontrado() throws Exception {
        Mockito.when(service.getFilme("barbie")).thenReturn(Optional.empty());
        this.mockMvc.perform(get("/filmes/barbie")).andExpect(status().isNotFound());

        Filme barbie = new Filme();
        barbie.setId("barbie-2023");
        barbie.setTitulo("Barbie");
        barbie.setDuracao(114);
        barbie.setClassificacao(12);
        barbie.setFormato(Filme.Formato._2D);

        Mockito.when(service.getFilme(barbie.getId())).thenReturn(Optional.ofNullable(barbie));
        this.mockMvc.perform(get(String.format("/filmes/%s", barbie.getId()))).andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"id\":\"barbie-2023\",\"titulo\":\"Barbie\",\"duracao\":114,\"classificacao\":12,\"formato\":\"2D\"}"));
    }

}
