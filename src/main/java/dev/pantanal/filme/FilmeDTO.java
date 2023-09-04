package dev.pantanal.filme;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmeDTO {

    @Null
    private String id;

    @NotBlank(message = "O título deve ter entre 1 e 255 caracteres.")
    private String titulo;

    @NotNull
    @Positive(message = "A duração é obrigatória.")
    private Integer duracao;

    @NotNull
    @Positive(message = "A classificação é obrigatória.")
    private Integer classificacao;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String imagem;

    @Pattern(regexp = "^(IMAX|3D|2D)$", message = "O formato deve ser IMAX, 3D ou 2D.")
    private String formato;

    @NotNull(message = "A data de lançamento é obrigatória.")
    private Date lancamento;
}
