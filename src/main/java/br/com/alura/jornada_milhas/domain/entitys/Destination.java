package br.com.alura.jornada_milhas.domain.entitys;

import br.com.alura.jornada_milhas.domain.dtos.requests.DestinationRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity(name = "Destination")
@Table(name = "destination")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "destination_name")
    private String destinationName;
    @Column(name = "picture_url")
    private String firstPictureUrl;
    @Column(name = "second_picture_url")
    private String secondPictureUrl;
    private String meta;
    @Column(name = "description_text", length = 1000)
    private String descriptionText;
    private BigDecimal price;

    public Destination(DestinationRequestDto dto) {
        this.destinationName = dto.destinationName();
        this.firstPictureUrl = dto.firstPictureUrl();
        this.secondPictureUrl = dto.secondPictureUrl();
        this.meta = dto.meta();
        this.descriptionText = dto.descriptionText();
        this.price = dto.price();
    }

    public void update(DestinationRequestDto dto) {
        if (dto.destinationName() != null) this.destinationName = dto.destinationName();
        if (dto.firstPictureUrl() != null) this.firstPictureUrl = dto.firstPictureUrl();
        if (dto.secondPictureUrl() != null) this.secondPictureUrl = dto.secondPictureUrl();
        if (dto.meta() != null) this.meta = dto.meta();
        if (dto.descriptionText() != null) this.descriptionText = dto.descriptionText();
        if (dto.price() != null) this.price = dto.price();
    }
}
