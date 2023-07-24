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
    private String pictureUrl;
    private BigDecimal price;

    public Destination(DestinationRequestDto dto) {
        this.destinationName = dto.destinationName();
        this.pictureUrl = dto.pictureUrl();
        this.price = dto.price();
    }

    public void update(DestinationRequestDto dto) {
        if (dto.destinationName() != null) this.destinationName = dto.destinationName();
        if (dto.pictureUrl() != null) this.pictureUrl = dto.pictureUrl();
        if (dto.price() != null) this.price = dto.price();
    }
}
