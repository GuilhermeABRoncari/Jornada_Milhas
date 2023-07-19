package br.com.alura.jornada_milhas.domain.dtos.responses;

import br.com.alura.jornada_milhas.domain.entitys.Testimony;

import java.time.OffsetDateTime;

public record TestimonyResponseDto(String id, String author, String testimony, String profilePicture, Boolean edited, OffsetDateTime postTime) {
    public TestimonyResponseDto(Testimony testimony) {
        this(testimony.getId(), testimony.getAuthor(), testimony.getDescription(), testimony.getProfilePicture(), testimony.getEdited(), testimony.getPostTime());
    }
}
