package dev.vitorvidal.petmanagementapi.domain.service.impl;

import dev.vitorvidal.petmanagementapi.domain.model.CreatePet;
import dev.vitorvidal.petmanagementapi.domain.model.Pet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PetServiceImpl {

    public Pet getPetById(UUID userId, UUID petId) {
        return null;
    }

    public List<Pet> getPetByUser(UUID userId, int pageSize) {
        return null;

    }

    public Pet createPet(CreatePet createPet, UUID userId) {
        return null;
    }

    public void deletePet(UUID userId, UUID petId) {
    }
}
