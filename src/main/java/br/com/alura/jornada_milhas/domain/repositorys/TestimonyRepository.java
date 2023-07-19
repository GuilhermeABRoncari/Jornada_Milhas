package br.com.alura.jornada_milhas.domain.repositorys;

import br.com.alura.jornada_milhas.domain.entitys.Testimony;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestimonyRepository extends JpaRepository<Testimony, String> {
}
