package dev.vitorvidal.petmanagementapi.service;

import dev.vitorvidal.petmanagementapi.domain.entity.PetEntity;
import dev.vitorvidal.petmanagementapi.domain.model.CreatePet;
import dev.vitorvidal.petmanagementapi.domain.model.Pet;
import dev.vitorvidal.petmanagementapi.domain.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.SliceImpl;
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

        Pet pet = petService.getPetById(userIdMock, petIdMock);

        verify(petRepository).findById(petIdMock);

        assertNotNull(pet);
        assertEquals(petIdMock, pet.petId());
        assertEquals(petNameMock, pet.petName());
        assertEquals(birthDateMock, pet.birthDate());
        assertEquals(speciesMock, pet.species());
        assertEquals(breedMock, pet.breed());
        assertEquals(creationDateMock, pet.creationDate());
        assertEquals(userIdMock, pet.userId());
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
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Pet not found", exception.getReason());
    }

    @Test
    void shouldCreatePetCorrectly() {
        CreatePet createPetMock = mock(CreatePet.class);
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

        Pet pet = petService.createPet(createPetMock, userIdMock);

        assertNotNull(pet);
        assertEquals(petIdMock, pet.petId());
        assertEquals(petNameMock, pet.petName());
        assertEquals(birthDateMock, pet.birthDate());
        assertEquals(speciesMock, pet.species());
        assertEquals(breedMock, pet.breed());
        assertEquals(creationDateMock, pet.creationDate());
        assertEquals(userIdMock, pet.userId());

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

        verify(petRepository).findById(petIdMock);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenDeletingPet() {
        UUID petIdMock = UUID.randomUUID();
        UUID userIdMock = UUID.randomUUID();

        when(petRepository.findById(petIdMock)).thenReturn(Optional.empty());
        doNothing().when(petRepository).deleteById(petIdMock);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> petService.deletePet(userIdMock, petIdMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Pet not found", exception.getReason());
    }

    @Test
    void shouldGetPetByUserCorrectly() {
        PetEntity petEntityMock = mock(PetEntity.class);
        UUID userIdMock = UUID.randomUUID();
        CassandraPageRequest pageRequestMock = CassandraPageRequest.first(10);
        List<PetEntity> petEntityList = List.of(petEntityMock);
        SliceImpl<PetEntity> petEntitySliceMock = new SliceImpl<>(petEntityList);

        UUID petIdMock = UUID.randomUUID();
        String petNameMock = "pet name";
        LocalDateTime birthDateMock = LocalDateTime.now();
        String speciesMock = "species";
        String breedMock = "breed";
        LocalDateTime creationDateMock = LocalDateTime.now();

        when(petRepository.findByUserId(userIdMock, pageRequestMock)).thenReturn(petEntitySliceMock);
        when(petEntityMock.getUserId()).thenReturn(userIdMock);
        when(petEntityMock.getPetId()).thenReturn(petIdMock);
        when(petEntityMock.getName()).thenReturn(petNameMock);
        when(petEntityMock.getBirthDate()).thenReturn(birthDateMock);
        when(petEntityMock.getSpecies()).thenReturn(speciesMock);
        when(petEntityMock.getBreed()).thenReturn(breedMock);
        when(petEntityMock.getCreationDate()).thenReturn(creationDateMock);

        List<Pet> petByUser = petService.getPetByUser(userIdMock, 10);

        assertNotNull(petByUser);
        assertEquals(1, petByUser.size());

        Pet pet = petByUser.get(0);

        assertEquals(petEntityMock.getUserId(), pet.userId());
        assertEquals(petEntityMock.getPetId(), pet.petId());
        assertEquals(petEntityMock.getName(), pet.petName());
        assertEquals(petEntityMock.getBreed(), pet.breed());
        assertEquals(petEntityMock.getSpecies(), pet.species());
        assertEquals(petEntityMock.getBirthDate(), pet.birthDate());

        verify(petRepository).findByUserId(userIdMock, pageRequestMock);
    }
}