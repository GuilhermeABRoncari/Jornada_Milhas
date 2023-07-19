package br.com.alura.jornada_milhas.domain.dtos.requests;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public record TestimonyRequestDto(
        String id,
        @NotBlank
        @JsonAlias("foto")
        String profilePicture,
        @NotBlank
        @JsonAlias("depoimento")
        String testimony,
        @NotBlank
        @JsonAlias("autor")
        String author) {
}
