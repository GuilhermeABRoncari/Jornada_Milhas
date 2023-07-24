package br.com.alura.jornada_milhas.domain.repositorys;

import br.com.alura.jornada_milhas.domain.entitys.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, String> {

    List<Destination> findAllByDestinationNameLikeIgnoreCase(String search);

}
