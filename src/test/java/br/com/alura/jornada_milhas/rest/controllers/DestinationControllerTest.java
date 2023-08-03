package br.com.alura.jornada_milhas.rest.controllers;

import br.com.alura.jornada_milhas.domain.dtos.requests.DestinationRequestDto;
import br.com.alura.jornada_milhas.domain.dtos.responses.DestinationResponseDto;
import br.com.alura.jornada_milhas.domain.entitys.Destination;
import br.com.alura.jornada_milhas.rest.service.DestinationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DestinationControllerTest {

    private DestinationController controller;
    @Mock
    private DestinationService service;
    private Pageable page;

    private DestinationRequestDto completeDestinationRequestDto;
    private DestinationRequestDto whitoutDescriptionTextDestinationRequestDto;
    private List<DestinationResponseDto> destinationsResponse;
    private Page<DestinationResponseDto> responseDtoPage;
    private DestinationRequestDto editedDestinationRequestDto;

    private Destination firstDestination;
    private DestinationResponseDto firstDestinationResponseDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new DestinationController(service);
        destinationsResponse = new ArrayList<>();

        completeDestinationRequestDto = new DestinationRequestDto(
                "Belo Horizonte", "pictureURL.com", "pictureURL.com",
                "meta description", "Gracefully description", new BigDecimal("100.00"));

        whitoutDescriptionTextDestinationRequestDto = new DestinationRequestDto(
                "Belo Horizonte", "pictureURL.com", "pictureURL.com",
                "meta description", "", new BigDecimal("100.00"));

        editedDestinationRequestDto = new DestinationRequestDto(
                "Perdigão", null, null, null, "Gracefully description",
                null);

        firstDestination = new Destination(completeDestinationRequestDto);
        firstDestinationResponseDto = new DestinationResponseDto(firstDestination);
        destinationsResponse.add(firstDestinationResponseDto);
        responseDtoPage = new PageImpl<>(destinationsResponse);

        page = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 20;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return Sort.unsorted();
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
    }

    @Test
    @DisplayName("Test post method whit correct new destination and expect status code 201 CREATED")
    void testPostMethodScene01() {
        // Given
        when(service.create(completeDestinationRequestDto)).thenReturn(firstDestinationResponseDto);

        // When
        ResponseEntity<DestinationResponseDto> controllerResponse = controller.post(completeDestinationRequestDto);

        // Then
        assertEquals(HttpStatus.CREATED, controllerResponse.getStatusCode());
        verify(service).create(completeDestinationRequestDto);
    }

    @Test
    @DisplayName("Test post method whit destination without descriptionText and expect status code 201 CREATED whit AI generated descriptionText")
    void testPostMethodScene02() {
        // Given
        when(service.create(whitoutDescriptionTextDestinationRequestDto)).thenReturn(firstDestinationResponseDto);

        // When
        ResponseEntity<DestinationResponseDto> controllerResponse = controller.post(whitoutDescriptionTextDestinationRequestDto);

        // Then
        assertEquals(HttpStatus.CREATED, controllerResponse.getStatusCode());
        assertFalse(Objects.requireNonNull(controllerResponse.getBody()).descriptionText().isBlank());
        verify(service).create(whitoutDescriptionTextDestinationRequestDto);
    }

    @Test
    @DisplayName("Test findAll method and expect status code 200 OK")
    void testFinAllMethodScene01() {
        // Given
        when(service.findAll(page)).thenReturn(responseDtoPage);

        // When
        ResponseEntity<Page<DestinationResponseDto>> controllerResponse = controller.getAll((org.springframework.data.domain.Pageable) page);

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
        verify(service).findAll(page);
    }

    @Test
    @DisplayName("Test edit method and expect status code 200 OK")
    void testEditMethodScene01() {
        // Given
        firstDestination.update(editedDestinationRequestDto);
        DestinationResponseDto editedDestinationResponseDto = new DestinationResponseDto(firstDestination);
        when(service.editDestinationById(firstDestination.getId(), editedDestinationRequestDto)).thenReturn(editedDestinationResponseDto);

        // When
        ResponseEntity<DestinationResponseDto> controllerResponse = controller.edit(firstDestination.getId(), editedDestinationRequestDto);

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
        verify(service).editDestinationById(firstDestination.getId(), editedDestinationRequestDto);
    }

    @Test
    @DisplayName("Test delete method and expect status code 204 NO_CONTENT")
    void testDeleteMethodScene01() {
        // Given
        destinationsResponse.remove(firstDestinationResponseDto);

        // When
        ResponseEntity<HttpStatus> controllerResponse = controller.delete(firstDestination.getId());

        // Then
        assertTrue(destinationsResponse.isEmpty());
        assertEquals(HttpStatus.NO_CONTENT, controllerResponse.getStatusCode());
    }

    @Test
    @DisplayName("Test search method and expect status code 200 OK")
    void testSearchMethodeScene01() {
        // Given
        String query = "bel";
        when(service.searchByClientQuery(query)).thenReturn(destinationsResponse);

        // When
        ResponseEntity<List<DestinationResponseDto>> controllerResponse = controller.search(query);

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
        assertEquals(destinationsResponse, controllerResponse.getBody());
        verify(service).searchByClientQuery(query);
    }

    @Test
    @DisplayName("Test getById method and expect status code 200 OK")
    void testGetByIdMethodScene01() {
        // Given
        when(service.findById(firstDestination.getId())).thenReturn(firstDestinationResponseDto);

        // When
        ResponseEntity<DestinationResponseDto> controllerResponse = controller.getById(firstDestination.getId());

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
        verify(service).findById(firstDestination.getId());
    }
}