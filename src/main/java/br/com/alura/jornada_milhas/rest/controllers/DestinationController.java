package br.com.alura.jornada_milhas.rest.controllers;

import br.com.alura.jornada_milhas.domain.dtos.requests.DestinationRequestDto;
import br.com.alura.jornada_milhas.domain.dtos.responses.DestinationResponseDto;
import br.com.alura.jornada_milhas.rest.service.DestinationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/destinos")
public class DestinationController {

    private final DestinationService service;

    @GetMapping("{id}")
    public ResponseEntity<DestinationResponseDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<DestinationResponseDto> post(@RequestBody @Valid DestinationRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping("/todos")
    public ResponseEntity<Page<DestinationResponseDto>> getAll(Pageable page) {
        return ResponseEntity.ok(service.findAll(page));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DestinationResponseDto> edit(@PathVariable String id, @RequestBody DestinationRequestDto dto) {
        return ResponseEntity.ok(service.editDestinationById(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<DestinationResponseDto>> search(@RequestParam(name = "nome") String search) {
        return ResponseEntity.ok(service.searchByClientQuery(search));
    }
}
