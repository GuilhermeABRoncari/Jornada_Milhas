package br.com.alura.jornada_milhas.domain.repositorys;

import br.com.alura.jornada_milhas.domain.entitys.Testimony;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestimonyRepository extends JpaRepository<Testimony, String> {

    @Query("SELECT t FROM Testimony t ORDER BY FUNCTION('RANDOM') LIMIT 3")
    List<Testimony> selectRandomThreeDepositions();

}
