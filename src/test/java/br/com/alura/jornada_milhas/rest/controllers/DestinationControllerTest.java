package br.com.alura.jornada_milhas.rest.controllers;

import br.com.alura.jornada_milhas.domain.dtos.requests.DestinationRequestDto;
import br.com.alura.jornada_milhas.domain.dtos.responses.DestinationResponseDto;
import br.com.alura.jornada_milhas.domain.entitys.Destination;
import br.com.alura.jornada_milhas.domain.repositorys.DestinationRepository;
import br.com.alura.jornada_milhas.infra.exceptions.InternalEntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DestinationControllerTest {

    private DestinationController controller;
    @Mock
    private DestinationRepository repository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new DestinationController(repository);
    }

    @Test
    void testPostMethodWhitValidRequestBody() {
        // Given
        String destinationName = "destination";
        String pictureUrl = "Picture URL";
        BigDecimal price = new BigDecimal("10.00");
        var body = new DestinationRequestDto(destinationName, pictureUrl, price);
        var destination = new Destination(body);
        var responseDto = new DestinationResponseDto(destination);
        when(repository.save(destination)).thenReturn(destination);

        // When
        ResponseEntity<DestinationResponseDto> controllerResponse = controller.post(body);

        // Then
        assertEquals(HttpStatus.CREATED, controllerResponse.getStatusCode());
        assertEquals(responseDto, controllerResponse.getBody());
        verify(repository).save(destination);
    }

    @Test
    void testGetAllMethod() {
        // Given
        List<Destination> destinations = new ArrayList<>();
        destinations.add(new Destination("ID", "Destination 1", "URL 1", BigDecimal.valueOf(100)));
        destinations.add(new Destination("ID", "Destination 2", "URL 2", BigDecimal.valueOf(200)));

        Page<Destination> page = new PageImpl<>(destinations);
        when(repository.findAll(any(PageRequest.class))).thenReturn(page);

        // When
        ResponseEntity<Page<DestinationResponseDto>> controllerResponse = controller.getAll(PageRequest.of(0, 10));

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
        assertEquals(2, Objects.requireNonNull(controllerResponse.getBody()).getTotalElements());
        verify(repository).findAll(any(PageRequest.class));
    }

    @Test
    void testEditMethod() {
        // Given
        String id = "ID";
        String destinationName = "Destination";
        String newDestinationName = "New destination name";
        String pictureUrl = "URL";
        BigDecimal price = new BigDecimal("500.00");
        Destination destination = new Destination(id, destinationName, pictureUrl, price);

        DestinationRequestDto dto = new DestinationRequestDto(newDestinationName, null, null);
        when(repository.findById(id)).thenReturn(Optional.of(destination));

        // When
        ResponseEntity<DestinationResponseDto> controllerResponse = controller.edit(id, dto);

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
        assertNotEquals(destinationName, Objects.requireNonNull(controllerResponse.getBody()).destinationName());
        verify(repository).findById(id);
    }

    @Test
    void testEditMethodThenCatchInternalEntityNotFoundException() {
        // Given
        String id = "ID";
        String destinationName = "Destination";
        String pictureUrl = "URL";
        BigDecimal price = new BigDecimal("500.00");

        // When // Then
        assertThrows(InternalEntityNotFoundException.class, () -> {
            ResponseEntity<DestinationResponseDto> controllerResponse = controller.edit(id, new DestinationRequestDto(destinationName, pictureUrl, price));
            assertEquals(HttpStatus.NOT_FOUND, controllerResponse.getStatusCode());
        });
    }

    @Test
    void testDeleteMethod() {
        // Given
        String id = "ID";

        // When
        ResponseEntity<HttpStatus> response = controller.delete(id);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testSearchMethod() {
        // Given
        String search = "bel$..;´´";
        String sanitizedSearch = search.trim();

        String id = "ID";
        String destinationName = "Belo Horizonte";
        String pictureUrl = "URL";
        BigDecimal price = new BigDecimal("500.00");
        Destination destination = new Destination(id, destinationName, pictureUrl, price);
        List<Destination> listResult = new ArrayList<>();
        listResult.add(destination);
        when(repository.findAllByDestinationNameLikeIgnoreCase("%" + sanitizedSearch + "%")).thenReturn(listResult);

        // When
        ResponseEntity<List<DestinationResponseDto>> response = controller.search(search);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listResult.size(), response.getBody().size());
        verify(repository).findAllByDestinationNameLikeIgnoreCase("%" + sanitizedSearch + "%");
    }

    @Test
    void testSearchMethodThenCatchInternalEntityNotFoundException() {
        // Given
        String search = "123456";
        String exceptionMessage = "Nenhum destino encontrado";

        // When // Then
        assertThrows(InternalEntityNotFoundException.class, () -> {
            ResponseEntity<List<DestinationResponseDto>> response = controller.search(search);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals(exceptionMessage, response.getBody());
        });
    }
}