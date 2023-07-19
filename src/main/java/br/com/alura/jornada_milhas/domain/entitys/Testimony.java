package br.com.alura.jornada_milhas.domain.entitys;

import br.com.alura.jornada_milhas.domain.dtos.requests.TestimonyRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity(name = "Testimony")
@Table(name = "testimony")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Testimony {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String profilePicture;
    String description;
    String author;
    OffsetDateTime postTime;
    Boolean edited;

    public Testimony(TestimonyRequestDto testimonyRequestDto) {
        this.profilePicture = testimonyRequestDto.profilePicture();
        this.description = testimonyRequestDto.testimony();
        this.author = testimonyRequestDto.author();
        this.postTime = OffsetDateTime.now();
        this.edited = false;
    }

    public void update(TestimonyRequestDto testimonyRequestDto) {
        this.edited = true;
        if (testimonyRequestDto.profilePicture() != null) this.profilePicture = testimonyRequestDto.profilePicture();
        if (testimonyRequestDto.testimony() != null) this.description = testimonyRequestDto.testimony();
        if (testimonyRequestDto.author() != null) this.author = testimonyRequestDto.author();
    }
}
