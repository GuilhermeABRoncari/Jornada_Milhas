package br.com.alura.jornada_milhas.rest.service;

import br.com.alura.jornada_milhas.domain.dtos.requests.DestinationRequestDto;
import br.com.alura.jornada_milhas.domain.dtos.responses.DestinationResponseDto;
import br.com.alura.jornada_milhas.domain.entitys.Destination;
import br.com.alura.jornada_milhas.domain.repositorys.DestinationRepository;
import br.com.alura.jornada_milhas.infra.exceptions.InternalEntityNotFoundException;
import br.com.alura.jornada_milhas.integrations.openia.OpenAIIntegrationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DestinationService {

    private final DestinationRepository repository;
    private final OpenAIIntegrationService integrationService;
    private static final String ANY_NOT_FOUND = "Nenhum destino foi encontrado";
    private static final String NOT_FOUND = "Destino não encontrado";
    private static final String NOT_FOUND_BY_ID = "Destino não encontrado pelo id: %s";

    @Transactional
    public DestinationResponseDto create(DestinationRequestDto dto) {
        var destination = new Destination(dto);
        if (destination.getDescriptionText().isBlank()) {
            destination.update(new DestinationRequestDto(
                    null,
                    null,
                    null,
                    null,
                    integrationService.generateText(destination.getDestinationName()),
                    null));
        }

        return new DestinationResponseDto(repository.save(destination));
    }

    public Page<DestinationResponseDto> findAll(Pageable page) {
        return repository.findAll(page).map(DestinationResponseDto::new);
    }

    @Transactional
    public DestinationResponseDto editDestinationById(String id, DestinationRequestDto dto) {
        Destination destination = repository.findById(id).orElseThrow(() -> new InternalEntityNotFoundException(NOT_FOUND));
        destination.update(dto);
        return  new DestinationResponseDto(destination);
    }

    @Transactional
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public List<DestinationResponseDto> searchByClientQuery(String search) {
        String sanitizedSearch = search.trim();
        List<DestinationResponseDto> response = new ArrayList<>();
        List<Destination> destinations = repository.findAllByDestinationNameLikeIgnoreCase("%" + sanitizedSearch + "%");
        destinations.forEach(destination -> response.add(new DestinationResponseDto(destination)));

        if(response.isEmpty()) throw new InternalEntityNotFoundException(ANY_NOT_FOUND);

        return response;
    }

    public DestinationResponseDto findById(String id) {
        return new DestinationResponseDto(repository.findById(id).orElseThrow(() -> new InternalEntityNotFoundException(NOT_FOUND_BY_ID.formatted(id))));
    }
}
