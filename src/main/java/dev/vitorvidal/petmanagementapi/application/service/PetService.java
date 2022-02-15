package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.application.repository.PetRepository;
import dev.vitorvidal.petmanagementapi.model.pet.CreatePetDTO;
import dev.vitorvidal.petmanagementapi.model.pet.PetDTO;
import dev.vitorvidal.petmanagementapi.model.pet.PetEntity;
import org.springframework.stereotype.Service;

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

    public List<PetDTO> listEveryPet() {
        List<PetEntity> petList = petRepository.findAll();

        List<PetDTO> petDTOList = new ArrayList<>();
        for (PetEntity pet : petList) {
            petDTOList.add(new PetDTO(
                    pet.getPetId(),
                    pet.getName(),
                    pet.getBirthDate(),
                    pet.getSpecies(),
                    pet.getBreed(),
                    pet.getCreationDate(),
                    pet.getUserId()
            ));
        }

        return petDTOList;
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
        return null;
    }

    public List<PetDTO> getPetByUser(UUID userId) {
        Optional<List<PetEntity>> optionalPetEntityList = petRepository.findByUserId(userId);

        if (optionalPetEntityList.isPresent()) {
            List<PetEntity> petEntities = optionalPetEntityList.get();

            List<PetDTO> petDTOList = new ArrayList<>();
            for (PetEntity pet : petEntities) {
                petDTOList.add(new PetDTO(
                        pet.getPetId(),
                        pet.getName(),
                        pet.getBirthDate(),
                        pet.getSpecies(),
                        pet.getBreed(),
                        pet.getCreationDate(),
                        pet.getUserId()
                ));
            }
            return petDTOList;
        }

        throw new RuntimeException("This user has no pet registers");
    }

    public PetDTO createPet(CreatePetDTO createPetDTO, UUID userId) {
        PetEntity pet = petRepository.save(new PetEntity(
                createPetDTO.name(),
                createPetDTO.birthDate(),
                createPetDTO.species(),
                createPetDTO.breed(),
                userId
        ));

        return new PetDTO(
                pet.getPetId(),
                pet.getName(),
                pet.getBirthDate(),
                pet.getSpecies(),
                pet.getBreed(),
                pet.getCreationDate(),
                pet.getUserId()
        );
    }

    public void deletePet(UUID petId) {
        petRepository.deleteById(petId);
    }
}
