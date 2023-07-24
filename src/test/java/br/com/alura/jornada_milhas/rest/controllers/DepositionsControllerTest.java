package br.com.alura.jornada_milhas.rest.controllers;

import br.com.alura.jornada_milhas.domain.dtos.requests.TestimonyRequestDto;
import br.com.alura.jornada_milhas.domain.dtos.responses.TestimonyResponseDto;
import br.com.alura.jornada_milhas.domain.entitys.Testimony;
import br.com.alura.jornada_milhas.domain.repositorys.TestimonyRepository;
import br.com.alura.jornada_milhas.infra.exceptions.InternalEntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DepositionsControllerTest {

    private DepositionsController controller;
    @Mock
    private TestimonyRepository repository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new DepositionsController(repository);
    }

    @Test
    void testPostMethodInEndPointDepoimentosWhitValidRequestBody() {
        // Given
        String author = "User";
        String testimony = "Gracefully overview";
        String profilePicture = "Profile picture url";
        var requestDto = new TestimonyRequestDto(null, profilePicture, testimony, author);
        var responseTestimony = new Testimony(requestDto);
        var responseDto = new TestimonyResponseDto(null, author, testimony, profilePicture, false, responseTestimony.getPostTime());
        when(repository.save(new Testimony(requestDto))).thenReturn(responseTestimony);

        // When
        ResponseEntity<TestimonyResponseDto> controllerResponse = controller.post(requestDto);

        // Then
        assertEquals(HttpStatus.CREATED, controllerResponse.getStatusCode());
        assertEquals(responseDto, controllerResponse.getBody());
        verify(repository).save(new Testimony(requestDto));
    }

    @Test
    void testPostMethodInEndPointDepoimentosWhitInvalidRequestBody() {
        // Given
        String invalidAuthor = "";
        String invalidTestimony = "";
        String invalidProfilePicture = "";
        var invalidRequestDto = new TestimonyRequestDto(null, invalidProfilePicture, invalidTestimony, invalidAuthor);

        // When // Then
        assertThrows(Exception.class, () -> {
            ResponseEntity<TestimonyResponseDto> controllerResponse = controller.post(invalidRequestDto);
            assertEquals(HttpStatus.BAD_REQUEST, controllerResponse.getStatusCode());
        });
    }

    @Test
    void testGetAllMethodInEndPointDepoimentos() {
        // Given
        List<TestimonyResponseDto> response = new ArrayList<>();
        List<Testimony> testimonyList = new ArrayList<>();
        when(repository.findAll()).thenReturn(testimonyList);

        // When
        ResponseEntity<List<TestimonyResponseDto>> controllerResponse = controller.getAll();

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
        assertEquals(response, controllerResponse.getBody());
        verify(repository).findAll();
    }

    @Test
    void testEditMethodInEndPointDepoimentosWhitValidRequestBody() {
        // Given
        String id = "id";
        String profilePicture = "Profile picture URL";
        String testimony = "Gracefully overview";
        String author = "User";

        var requestedBody = new TestimonyRequestDto(id, profilePicture, testimony, author);
        var expectedResult = new Testimony(id, profilePicture, testimony, author, OffsetDateTime.now(), true);
        when(repository.findById(id)).thenReturn(Optional.of(expectedResult));
        var responseDto = new TestimonyResponseDto(expectedResult);

        // When
        ResponseEntity<TestimonyResponseDto> controllerResponse = controller.edit(requestedBody);

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
        assertEquals(responseDto, controllerResponse.getBody());
        verify(repository).findById(id);
    }

    @Test
    void testEditMethodInEndPointDepoimentosWhitInvalidRequestBody() {
        // Given
        String invalidId = "id";
        String profilePicture = "Profile picture URL";
        String testimony = "Gracefully overview";
        String author = "User";

        var requestedBody = new TestimonyRequestDto(invalidId, profilePicture, testimony, author);

        // Then // When
        assertThrows(InternalEntityNotFoundException.class, () -> {
            ResponseEntity<TestimonyResponseDto> controllerResponse = controller.edit(requestedBody);
            assertEquals(HttpStatus.BAD_REQUEST, controllerResponse.getStatusCode());
        });
    }

    @Test
    void testDeleteMethodInEndPointDepoimentos() {
        // Given
        String id = "id";

        // When
        ResponseEntity<HttpStatus> response = controller.delete(id);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetRandomThreeDepositionsMethodInEndPointDepoimentosHome() {
        // Given
        List<TestimonyResponseDto> expectedResponse = new ArrayList<>();
        List<Testimony> repositoryResponse = new ArrayList<>();
        String id1 = "id1L";
        String id2 = "id2L";
        String id3 = "id3L";
        String author = "User";
        String testimony = "Gracefully overview";
        String profilePicture = "Profile picture URL";
        OffsetDateTime postedAt = OffsetDateTime.now();
        var result1 = new Testimony(id1, profilePicture, testimony, author, postedAt, false);
        var result2 = new Testimony(id2, profilePicture, testimony, author, postedAt, false);
        var result3 = new Testimony(id3, profilePicture, testimony, author, postedAt, false);
        repositoryResponse.add(result1);
        repositoryResponse.add(result2);
        repositoryResponse.add(result3);

        when(repository.findAll()).thenReturn(repositoryResponse);
        repositoryResponse.forEach(result -> expectedResponse.add(new TestimonyResponseDto(result)));

        // When
        ResponseEntity<List<TestimonyResponseDto>> controllerResponse = controller.getRandomThreeDepositions();

        // Then
        assertEquals(HttpStatus.OK, controllerResponse.getStatusCode());
        assertEquals(expectedResponse.getClass(), controllerResponse.getBody().getClass());
        assertEquals(expectedResponse.size(), controllerResponse.getBody().size());
        verify(repository).findAll();
    }
}