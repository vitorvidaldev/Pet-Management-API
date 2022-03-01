package dev.vitorvidal.petmanagementapi.application.controller;

import dev.vitorvidal.petmanagementapi.application.service.PetService;
import dev.vitorvidal.petmanagementapi.model.dto.CreatePetDTO;
import dev.vitorvidal.petmanagementapi.model.dto.PetDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PetControllerTest {
    @InjectMocks
    private PetController petController;
    @Mock
    private PetService petService;

    @Test
    void shouldGetPetByIdCorrectly() {
        UUID petIdMock = UUID.randomUUID();
        UUID userIdMock = UUID.randomUUID();
        PetDTO petDTOMock = mock(PetDTO.class);

        when(petService.getPetById(userIdMock, petIdMock)).thenReturn(petDTOMock);
        ResponseEntity<PetDTO> response = petController.getPetById(userIdMock, petIdMock);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(petDTOMock, response.getBody());
    }

    @Test
    void shouldGetPetByUserCorrectly() {
        UUID userIdMock = UUID.randomUUID();
        PetDTO petDTOMock = mock(PetDTO.class);
        List<PetDTO> petDTOList = List.of(petDTOMock);

        when(petService.getPetByUser(userIdMock)).thenReturn(petDTOList);
        ResponseEntity<List<PetDTO>> response = petController
                .getPetByUser(userIdMock);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(petDTOMock, response.getBody().get(0));
    }

    @Test
    void shouldCreatePetCorrectly() {
        UUID userIdMock = UUID.randomUUID();
        CreatePetDTO createPetDTOMock = mock(CreatePetDTO.class);
        PetDTO petDTOMock = mock(PetDTO.class);

        when(petService.createPet(createPetDTOMock, userIdMock))
                .thenReturn(petDTOMock);

        ResponseEntity<PetDTO> response =
                petController.createPet(createPetDTOMock, userIdMock);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(petDTOMock, response.getBody());
    }

    @Test
    void shouldDeletePetCorrectly() {
        UUID petIdMock = UUID.randomUUID();
        UUID userIdMock = UUID.randomUUID();

        doNothing().when(petService).deletePet(userIdMock, petIdMock);
        ResponseEntity<Void> response = petController.deletePet(userIdMock, petIdMock);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}