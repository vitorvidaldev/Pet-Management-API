package dev.vitorvidal.petmanagementapi.application.controller;

import dev.vitorvidal.petmanagementapi.application.service.NoteService;
import dev.vitorvidal.petmanagementapi.model.dto.CreateNoteDTO;
import dev.vitorvidal.petmanagementapi.model.dto.NoteDTO;
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
class NoteControllerTest {
    @InjectMocks
    private NoteController noteController;
    @Mock
    private NoteService noteService;

    @Test
    void shouldGetNoteByIdCorrectly() {
        UUID noteIdMock = UUID.randomUUID();
        UUID userIdMock = UUID.randomUUID();
        NoteDTO noteDTOMock = mock(NoteDTO.class);

        when(noteService.getNoteById(userIdMock, noteIdMock)).thenReturn(noteDTOMock);
        ResponseEntity<NoteDTO> response = noteController.getNoteById(userIdMock, noteIdMock);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(noteDTOMock, response.getBody());
    }

    @Test
    void shouldCreateNoteCorrectly() {
        CreateNoteDTO createNoteDTOMock = mock(CreateNoteDTO.class);
        NoteDTO noteDTOMock = mock(NoteDTO.class);

        when(noteService.createNote(createNoteDTOMock)).thenReturn(noteDTOMock);
        ResponseEntity<NoteDTO> response =
                noteController.createNote(createNoteDTOMock);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(noteDTOMock, response.getBody());
    }

    @Test
    void shouldDeleteNoteCorrectly() {
        UUID noteIdMock = UUID.randomUUID();
        UUID userIdMock = UUID.randomUUID();

        doNothing().when(noteService).deleteNote(userIdMock, noteIdMock);
        ResponseEntity<Void> response = noteController.deleteNote(userIdMock, noteIdMock);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void shouldGetNoteByPetCorrectly() {
        UUID petIdMock = UUID.randomUUID();
        NoteDTO noteDTOMock = mock(NoteDTO.class);
        List<NoteDTO> noteDTOList = List.of(noteDTOMock);

        when(noteService.getNoteByPet(petIdMock)).thenReturn(noteDTOList);

        ResponseEntity<List<NoteDTO>> response = noteController.getNoteByPet(petIdMock);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(noteDTOList, response.getBody());

        verify(noteService).getNoteByPet(petIdMock);
    }

    @Test
    void shouldGetNoteByUserCorrectly() {
        UUID userIdMock = UUID.randomUUID();
        NoteDTO noteDTOMock = mock(NoteDTO.class);
        List<NoteDTO> noteDTOList = List.of(noteDTOMock);

        when(noteService.getNoteByUser(userIdMock)).thenReturn(noteDTOList);

        ResponseEntity<List<NoteDTO>> response = noteController.getNoteByUser(userIdMock);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(noteDTOList, response.getBody());

        verify(noteService).getNoteByUser(userIdMock);
    }
}