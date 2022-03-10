package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.model.dto.CreatePetDTO;
import dev.vitorvidal.petmanagementapi.model.dto.PetDTO;
import dev.vitorvidal.petmanagementapi.model.entity.PetEntity;
import dev.vitorvidal.petmanagementapi.model.repository.PetRepository;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public PetDTO getPetById(UUID userId, UUID petId) {
        Optional<PetEntity> optionalPet = petRepository.findById(petId);

        if (optionalPet.isPresent() && optionalPet.get().getUserId().equals(userId)) {
            PetEntity petEntity = optionalPet.get();
            if (petEntity.getUserId().equals(userId)) {
                return new PetDTO(
                        petEntity.getPetId(),
                        petEntity.getName(),
                        petEntity.getBirthDate(),
                        petEntity.getSpecies(),
                        petEntity.getBreed(),
                        petEntity.getCreationDate(),
                        petEntity.getUserId()
                );
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found");
    }

    public List<PetDTO> getPetByUser(UUID userId, int pageSize) {
        Slice<PetEntity> petEntitySlice = petRepository.findByUserId(userId, CassandraPageRequest.first(pageSize));
        List<PetEntity> petEntityList = petEntitySlice.getContent();
        List<PetDTO> petDTOList = new ArrayList<>();
        for (PetEntity petEntity : petEntityList) {
            if (petEntity.getUserId().equals(userId)) {
                petDTOList.add(new PetDTO(
                        petEntity.getPetId(),
                        petEntity.getName(),
                        petEntity.getBirthDate(),
                        petEntity.getSpecies(),
                        petEntity.getBreed(),
                        petEntity.getCreationDate(),
                        petEntity.getUserId()
                ));
            }
        }
        return petDTOList;
    }

    public PetDTO createPet(CreatePetDTO createPetDTO, UUID userId) {
        PetEntity petEntity = petRepository.save(new PetEntity(
                createPetDTO.name(),
                createPetDTO.birthDate(),
                createPetDTO.species(),
                createPetDTO.breed(),
                userId
        ));

        return new PetDTO(
                petEntity.getPetId(),
                petEntity.getName(),
                petEntity.getBirthDate(),
                petEntity.getSpecies(),
                petEntity.getBreed(),
                petEntity.getCreationDate(),
                petEntity.getUserId()
        );
    }

    public void deletePet(UUID userId, UUID petId) {
        Optional<PetEntity> optionalPet = petRepository.findById(petId);
        if (optionalPet.isEmpty() || !optionalPet.get().getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found");
        }

        PetEntity petEntity = optionalPet.get();
        if (petEntity.getUserId().equals(userId)) {
            petRepository.deleteById(petId);
        }
    }
}
