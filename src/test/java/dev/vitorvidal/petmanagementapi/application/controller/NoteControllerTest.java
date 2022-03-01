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
        NoteDTO noteDTOMock = mock(NoteDTO.class);

        when(noteService.getNoteById(noteIdMock)).thenReturn(noteDTOMock);
        ResponseEntity<NoteDTO> response = noteController.getNoteById(noteIdMock);

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
        UUID noteId = UUID.randomUUID();

        doNothing().when(noteService).deleteNote(noteId);
        ResponseEntity<Void> response = noteController.deleteNote(noteId);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }
}