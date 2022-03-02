package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.model.dto.CreatePetDTO;
import dev.vitorvidal.petmanagementapi.model.dto.PetDTO;
import dev.vitorvidal.petmanagementapi.model.entity.PetEntity;
import dev.vitorvidal.petmanagementapi.model.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class PetServiceTest {
    @InjectMocks
    private PetService petService;
    @Mock
    private PetRepository petRepository;

    @Test
    void shouldGetPetByIdCorrectly() {
        PetEntity petEntityMock = mock(PetEntity.class);

        UUID petIdMock = UUID.randomUUID();
        String petNameMock = "pet name";
        LocalDateTime birthDateMock = LocalDateTime.now();
        String speciesMock = "species";
        String breedMock = "breed";
        LocalDateTime creationDateMock = LocalDateTime.now();
        UUID userIdMock = UUID.randomUUID();

        when(petEntityMock.getPetId()).thenReturn(petIdMock);
        when(petEntityMock.getName()).thenReturn(petNameMock);
        when(petEntityMock.getBirthDate()).thenReturn(birthDateMock);
        when(petEntityMock.getSpecies()).thenReturn(speciesMock);
        when(petEntityMock.getBreed()).thenReturn(breedMock);
        when(petEntityMock.getCreationDate()).thenReturn(creationDateMock);
        when(petEntityMock.getUserId()).thenReturn(userIdMock);

        when(petRepository.findById(petIdMock)).thenReturn(Optional.of(petEntityMock));

        PetDTO petDTO = petService.getPetById(userIdMock, petIdMock);

        verify(petRepository).findById(petIdMock);

        assertNotNull(petDTO);
        assertEquals(petIdMock, petDTO.petId());
        assertEquals(petNameMock, petDTO.petName());
        assertEquals(birthDateMock, petDTO.birthDate());
        assertEquals(speciesMock, petDTO.species());
        assertEquals(breedMock, petDTO.breed());
        assertEquals(creationDateMock, petDTO.creationDate());
        assertEquals(userIdMock, petDTO.userId());
    }

    @Test
    void shouldThrowNotFoundExceptionGettingPet() {
        UUID petIdMock = UUID.randomUUID();
        UUID userIdMock = UUID.randomUUID();

        when(petRepository.findById(petIdMock)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> petService.getPetById(userIdMock, petIdMock));

        verify(petRepository).findById(petIdMock);

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Pet not found", exception.getReason());
    }

    @Test
    void shouldGetPetByUserCorrectly() {
        PetEntity petEntityMock = mock(PetEntity.class);
        List<PetEntity> petEntityListMock = List.of(petEntityMock);

        UUID userIdMock = UUID.randomUUID();
        UUID petIdMock = UUID.randomUUID();
        String petNameMock = "pet name";
        LocalDateTime birthDateMock = LocalDateTime.now();
        String speciesMock = "species";
        String breedMock = "breed";
        LocalDateTime creationDateMock = LocalDateTime.now();

        when(petEntityMock.getPetId()).thenReturn(petIdMock);
        when(petEntityMock.getName()).thenReturn(petNameMock);
        when(petEntityMock.getBirthDate()).thenReturn(birthDateMock);
        when(petEntityMock.getSpecies()).thenReturn(speciesMock);
        when(petEntityMock.getBreed()).thenReturn(breedMock);
        when(petEntityMock.getCreationDate()).thenReturn(creationDateMock);
        when(petEntityMock.getUserId()).thenReturn(userIdMock);

        when(petRepository.findByUserId(userIdMock)).thenReturn(Optional.of(petEntityListMock));

        List<PetDTO> petDTOList = petService.getPetByUser(userIdMock);

        verify(petRepository).findByUserId(userIdMock);

        assertNotNull(petDTOList);
        assertEquals(petEntityListMock.size(), petDTOList.size());
        assertNotNull(petDTOList.get(0));
        assertEquals(petIdMock, petDTOList.get(0).petId());
        assertEquals(petNameMock, petDTOList.get(0).petName());
        assertEquals(birthDateMock, petDTOList.get(0).birthDate());
        assertEquals(speciesMock, petDTOList.get(0).species());
        assertEquals(breedMock, petDTOList.get(0).breed());
        assertEquals(creationDateMock, petDTOList.get(0).creationDate());
        assertEquals(userIdMock, petDTOList.get(0).userId());
    }

    @Test
    void shouldThrowNotFoundExceptionGettingPetByUser() {
        UUID userIdMock = UUID.randomUUID();

        when(petRepository.findByUserId(userIdMock)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> petService.getPetByUser(userIdMock));

        verify(petRepository).findByUserId(userIdMock);

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Pets not found", exception.getReason());

    }

    @Test
    void shouldCreatePetCorrectly() {
        CreatePetDTO createPetDTOMock = mock(CreatePetDTO.class);
        PetEntity petEntityMock = mock(PetEntity.class);

        UUID petIdMock = UUID.randomUUID();
        String petNameMock = "pet name";
        LocalDateTime birthDateMock = LocalDateTime.now();
        String speciesMock = "species";
        String breedMock = "breed";
        LocalDateTime creationDateMock = LocalDateTime.now();
        UUID userIdMock = UUID.randomUUID();

        when(petEntityMock.getPetId()).thenReturn(petIdMock);
        when(petEntityMock.getName()).thenReturn(petNameMock);
        when(petEntityMock.getBirthDate()).thenReturn(birthDateMock);
        when(petEntityMock.getSpecies()).thenReturn(speciesMock);
        when(petEntityMock.getBreed()).thenReturn(breedMock);
        when(petEntityMock.getCreationDate()).thenReturn(creationDateMock);
        when(petEntityMock.getUserId()).thenReturn(userIdMock);

        when(petRepository.save(any(PetEntity.class))).thenReturn(petEntityMock);

        PetDTO petDTO = petService.createPet(createPetDTOMock, userIdMock);

        assertNotNull(petDTO);
        assertEquals(petIdMock, petDTO.petId());
        assertEquals(petNameMock, petDTO.petName());
        assertEquals(birthDateMock, petDTO.birthDate());
        assertEquals(speciesMock, petDTO.species());
        assertEquals(breedMock, petDTO.breed());
        assertEquals(creationDateMock, petDTO.creationDate());
        assertEquals(userIdMock, petDTO.userId());

        verify(petRepository).save(any(PetEntity.class));
    }

    @Test
    void shouldDeletePetCorrectly() {
        UUID petIdMock = UUID.randomUUID();
        UUID userIdMock = UUID.randomUUID();
        PetEntity petEntityMock = mock(PetEntity.class);
        Optional<PetEntity> optionalPet = Optional.of(petEntityMock);

        when(petRepository.findById(petIdMock)).thenReturn(optionalPet);
        when(optionalPet.get().getUserId()).thenReturn(userIdMock);
        doNothing().when(petRepository).deleteById(petIdMock);

        assertDoesNotThrow(() -> petService.deletePet(userIdMock, petIdMock));

        verify(petRepository).findByUserId(petIdMock);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenDeletingPet() {
        UUID petIdMock = UUID.randomUUID();
        UUID userIdMock = UUID.randomUUID();

        when(petRepository.findById(petIdMock)).thenReturn(Optional.empty());
        doNothing().when(petRepository).deleteById(petIdMock);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> petService.deletePet(userIdMock, petIdMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Pet not found", exception.getReason());
    }
}