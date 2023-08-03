package br.com.alura.jornada_milhas.domain.dtos.responses;

import br.com.alura.jornada_milhas.domain.entitys.Destination;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record DestinationResponseDto(
        String id,
        @JsonProperty("nome")
        String destinationName,
        @JsonProperty("foto_1")
        String firstPictureUrl,
        @JsonProperty("foto_2")
        String secondPictureUrl,
        String meta,
        @JsonProperty("text_descritivo")
        String descriptionText,
        @JsonProperty("preço")
        BigDecimal price
) {
    public DestinationResponseDto(Destination destination) {
        this(destination.getId(), destination.getDestinationName(), destination.getFirstPictureUrl(),
                destination.getSecondPictureUrl(), destination.getMeta(), destination.getDescriptionText(),
                destination.getPrice());
    }
}
