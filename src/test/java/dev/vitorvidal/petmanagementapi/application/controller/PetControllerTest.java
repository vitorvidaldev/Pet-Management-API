package dev.vitorvidal.petmanagementapi.application.controller;

import dev.vitorvidal.petmanagementapi.application.service.PetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class PetControllerTest {
    @InjectMocks
    private PetController petController;
    @Mock
    private PetService petService;

    @Test
    void shouldListEveryPetCorrectly() {
    }

    @Test
    void shouldGetPetByIdCorrectly() {
    }

    @Test
    void shouldGetPetByUserCorrectly() {
    }

    @Test
    void shouldCreatePetCorrectly() {
    }

    @Test
    void shouldDeletePetCorrectly() {
    }
}