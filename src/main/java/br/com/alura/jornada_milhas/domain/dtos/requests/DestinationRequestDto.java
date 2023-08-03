package br.com.alura.jornada_milhas.domain.dtos.requests;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record DestinationRequestDto(
        @NotBlank
        @JsonAlias("nome")
        String destinationName,
        @NotBlank
        @JsonAlias("foto_1")
        @Size(max = 255, message = "A url da imagem não deve possuir mais de 255 caracteres.")
        String firstPictureUrl,
        @NotBlank
        @JsonAlias("foto_2")
        @Size(max = 255, message = "A url da imagem não deve possuir mais de 255 caracteres.")
        String secondPictureUrl,
        @Size(max = 160, message = "A mensagem não pode ter mais de 160 caracteres.")
        @NotBlank
        String meta,
        @JsonAlias("texto_descritivo")
        String descriptionText,
        @NotNull
        @JsonAlias("preço")
        BigDecimal price
) {
}
