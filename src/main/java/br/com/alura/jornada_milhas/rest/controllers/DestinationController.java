package br.com.alura.jornada_milhas.rest.controllers;

import br.com.alura.jornada_milhas.domain.dtos.requests.DestinationRequestDto;
import br.com.alura.jornada_milhas.domain.dtos.responses.DestinationResponseDto;
import br.com.alura.jornada_milhas.domain.entitys.Destination;
import br.com.alura.jornada_milhas.domain.repositorys.DestinationRepository;
import br.com.alura.jornada_milhas.infra.exceptions.InternalEntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/destinos")
public class DestinationController {

    private final DestinationRepository repository;

    @PostMapping
    public ResponseEntity<DestinationResponseDto> post(@RequestBody @Valid DestinationRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new DestinationResponseDto(repository.save(new Destination(dto))));
    }

    @GetMapping("/todos")
    public ResponseEntity<Page<DestinationResponseDto>> getAll(Pageable page) {
        return ResponseEntity.ok(repository.findAll(page).map(DestinationResponseDto::new));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DestinationResponseDto> edit(@PathVariable String id, @RequestBody DestinationRequestDto dto) {
        Destination destination = repository.findById(id).orElseThrow(() -> new InternalEntityNotFoundException("Destino não encontrado"));
        destination.update(dto);
        return ResponseEntity.ok(new DestinationResponseDto(destination));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DestinationResponseDto>> search(@RequestParam(name = "nome") String search) {
        String sanitizedSearch = search.trim();
        List<DestinationResponseDto> response = new ArrayList<>();
        List<Destination> destinations = repository.findAllByDestinationNameLikeIgnoreCase("%" + sanitizedSearch + "%");
        destinations.forEach(destination -> response.add(new DestinationResponseDto(destination)));

        if(response.isEmpty()) throw new InternalEntityNotFoundException("Nenhum destino foi encontrado");

        return ResponseEntity.ok(response);
    }
}
