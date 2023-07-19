package br.com.alura.jornada_milhas.rest.controllers;

import br.com.alura.jornada_milhas.domain.dtos.requests.TestimonyRequestDto;
import br.com.alura.jornada_milhas.domain.dtos.responses.TestimonyResponseDto;
import br.com.alura.jornada_milhas.domain.entitys.Testimony;
import br.com.alura.jornada_milhas.domain.repositorys.TestimonyRepository;
import br.com.alura.jornada_milhas.infra.exceptions.TestimonyNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/depoimentos")
@AllArgsConstructor
public class DepositionsController {

    private TestimonyRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<TestimonyResponseDto> post(@RequestBody @Valid TestimonyRequestDto testimonyRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new TestimonyResponseDto(repository.save(new Testimony(testimonyRequestDto))));
    }

    @GetMapping
    public ResponseEntity<List<TestimonyResponseDto>> getAll() {
        List<TestimonyResponseDto> response = new ArrayList<>();
        repository.findAll().forEach(testimony -> response.add(new TestimonyResponseDto(testimony)));
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<TestimonyResponseDto> edit(@RequestBody TestimonyRequestDto testimonyRequestDto) {
        var testimony = repository.findById(testimonyRequestDto.id()).orElseThrow(() -> new TestimonyNotFoundException("Depoimento não encontrado"));
        testimony.update(testimonyRequestDto);
        return ResponseEntity.ok(new TestimonyResponseDto(testimony));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
