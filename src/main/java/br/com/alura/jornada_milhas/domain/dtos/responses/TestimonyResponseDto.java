package br.com.alura.jornada_milhas.domain.dtos.responses;

import br.com.alura.jornada_milhas.domain.entitys.Testimony;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record TestimonyResponseDto(
        String id,
        @JsonProperty("autor")
        String author,
        @JsonProperty("depoimento")
        String testimony,
        @JsonProperty("foto")
        String profilePicture,
        @JsonProperty("editado")
        Boolean edited,
        @JsonProperty("postado_as")
        OffsetDateTime postTime) {
    public TestimonyResponseDto(Testimony testimony) {
        this(testimony.getId(), testimony.getAuthor(), testimony.getDescription(), testimony.getProfilePicture(), testimony.getEdited(), testimony.getPostTime());
    }
}
