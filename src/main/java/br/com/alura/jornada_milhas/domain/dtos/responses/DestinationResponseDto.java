package br.com.alura.jornada_milhas.domain.dtos.responses;

import br.com.alura.jornada_milhas.domain.entitys.Destination;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record DestinationResponseDto(
        String id,
        @JsonProperty("nome")
        String destinationName,
        @JsonProperty("foto")
        String pictureUrl,
        @JsonProperty("preço")
        BigDecimal price
) {
    public DestinationResponseDto(Destination destination) {
        this(destination.getId(), destination.getDestinationName(), destination.getPictureUrl(), destination.getPrice());
    }
}
