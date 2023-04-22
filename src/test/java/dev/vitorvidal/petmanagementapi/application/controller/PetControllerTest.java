package dev.vitorvidal.petmanagementapi.application.controller;

import dev.vitorvidal.petmanagementapi.controller.PetController;
import dev.vitorvidal.petmanagementapi.domain.model.CreatePet;
import dev.vitorvidal.petmanagementapi.domain.model.Pet;
import dev.vitorvidal.petmanagementapi.service.PetService;
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
        Pet petMock = mock(Pet.class);

        when(petService.getPetById(userIdMock, petIdMock)).thenReturn(petMock);
        ResponseEntity<Pet> response = petController.getPetById(userIdMock, petIdMock);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(petMock, response.getBody());
    }

    @Test
    void shouldGetPetByUserCorrectly() {
        UUID userIdMock = UUID.randomUUID();
        Pet petMock = mock(Pet.class);
        List<Pet> petList = List.of(petMock);

        when(petService.getPetByUser(userIdMock, 10)).thenReturn(petList);
        ResponseEntity<List<Pet>> response = petController.getPetByUser(userIdMock, 10);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(petMock, response.getBody().get(0));
    }

    @Test
    void shouldCreatePetCorrectly() {
        UUID userIdMock = UUID.randomUUID();
        CreatePet createPetMock = mock(CreatePet.class);
        Pet petMock = mock(Pet.class);

        when(petService.createPet(createPetMock, userIdMock))
                .thenReturn(petMock);

        ResponseEntity<Pet> response =
                petController.createPet(createPetMock, userIdMock);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(petMock, response.getBody());
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