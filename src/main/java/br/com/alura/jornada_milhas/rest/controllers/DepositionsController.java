package br.com.alura.jornada_milhas.rest.controllers;

import br.com.alura.jornada_milhas.domain.dtos.requests.TestimonyRequestDto;
import br.com.alura.jornada_milhas.domain.dtos.responses.TestimonyResponseDto;
import br.com.alura.jornada_milhas.domain.entitys.Testimony;
import br.com.alura.jornada_milhas.domain.repositorys.TestimonyRepository;
import br.com.alura.jornada_milhas.infra.exceptions.InternalEntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@AllArgsConstructor
public class DepositionsController {

    private TestimonyRepository repository;

    @PostMapping("/depoimentos")
    @Transactional
    public ResponseEntity<TestimonyResponseDto> post(@RequestBody @Valid TestimonyRequestDto testimonyRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new TestimonyResponseDto(repository.save(new Testimony(testimonyRequestDto))));
    }

    @GetMapping("/depoimentos")
    public ResponseEntity<List<TestimonyResponseDto>> getAll() {
        List<TestimonyResponseDto> response = new ArrayList<>();
        repository.findAll().forEach(testimony -> response.add(new TestimonyResponseDto(testimony)));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/depoimentos")
    @Transactional
    public ResponseEntity<TestimonyResponseDto> edit(@RequestBody TestimonyRequestDto testimonyRequestDto) {
        var testimony = repository.findById(testimonyRequestDto.id()).orElseThrow(() -> new InternalEntityNotFoundException("Depoimento não encontrado"));
        testimony.update(testimonyRequestDto);
        return ResponseEntity.ok(new TestimonyResponseDto(testimony));
    }

    @DeleteMapping("/depoimentos/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/depoimentos-home")
    public ResponseEntity<List<TestimonyResponseDto>> getRandomThreeDepositions() {
        List<Testimony> all = repository.findAll();
        List<TestimonyResponseDto> response = new ArrayList<>();
        all.forEach(testimony -> response.add(new TestimonyResponseDto(testimony)));

        if (response.size() <= 3) {
            return ResponseEntity.ok(response);
        } else {
            List<TestimonyResponseDto> randomThreeDepositions = new ArrayList<>();
            Random random = new Random();

            while (randomThreeDepositions.size() < 3) {
                int randomIndex = random.nextInt(response.size());
                TestimonyResponseDto randomDeposition = response.get(randomIndex);

                if (!randomThreeDepositions.contains(randomDeposition)) {
                    randomThreeDepositions.add(randomDeposition);
                }
            }

            return ResponseEntity.ok(randomThreeDepositions);
        }

    }
}
