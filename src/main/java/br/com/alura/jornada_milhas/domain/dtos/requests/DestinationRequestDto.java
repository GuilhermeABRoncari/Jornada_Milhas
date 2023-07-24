package br.com.alura.jornada_milhas.domain.dtos.requests;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DestinationRequestDto(
        @NotBlank
        @JsonAlias("nome")
        String destinationName,
        @NotBlank
        @JsonAlias("foto")
        String pictureUrl,
        @NotNull
        @JsonAlias("preço")
        BigDecimal price
) {
}
