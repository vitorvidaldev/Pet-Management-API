package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.model.dto.CreatePetDTO;
import dev.vitorvidal.petmanagementapi.model.dto.PetDTO;
import dev.vitorvidal.petmanagementapi.model.entity.PetEntity;
import dev.vitorvidal.petmanagementapi.model.repository.PetRepository;
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

    public PetDTO getPetById(UUID petId) {
        Optional<PetEntity> optionalPet = petRepository.findById(petId);

        if (optionalPet.isPresent()) {
            PetEntity petEntity = optionalPet.get();
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
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet not found");
    }

    public List<PetDTO> getPetByUser(UUID userId) {
        Optional<List<PetEntity>> optionalPetEntityList = petRepository.findByUserId(userId);
        if (optionalPetEntityList.isPresent()) {
            List<PetEntity> petEntities = optionalPetEntityList.get();
            List<PetDTO> petDTOList = new ArrayList<>();
            for (PetEntity petEntity : petEntities) {
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
            return petDTOList;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pets not found");
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

    public void deletePet(UUID petId) {
        petRepository.deleteById(petId);
    }
}
