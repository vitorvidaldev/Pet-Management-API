package dev.vitorvidal.petmanagementapi.application.controller;

import dev.vitorvidal.petmanagementapi.controller.NoteController;
import dev.vitorvidal.petmanagementapi.domain.model.CreateNote;
import dev.vitorvidal.petmanagementapi.domain.model.Note;
import dev.vitorvidal.petmanagementapi.domain.service.impl.NoteServiceImpl;
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
    private NoteServiceImpl noteServiceImpl;

    @Test
    void shouldGetNoteByIdCorrectly() {
        UUID noteIdMock = UUID.randomUUID();
        UUID userIdMock = UUID.randomUUID();
        Note noteMock = mock(Note.class);

        when(noteServiceImpl.getNoteById(userIdMock, noteIdMock)).thenReturn(noteMock);
        ResponseEntity<Note> response = noteController.getNoteById(userIdMock, noteIdMock);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(noteMock, response.getBody());
    }

    @Test
    void shouldCreateNoteCorrectly() {
        CreateNote createNoteMock = mock(CreateNote.class);
        Note noteMock = mock(Note.class);

        when(noteServiceImpl.createNote(createNoteMock)).thenReturn(noteMock);
        ResponseEntity<Note> response = noteController.createNote(createNoteMock);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(noteMock, response.getBody());
    }

    @Test
    void shouldDeleteNoteCorrectly() {
        UUID noteIdMock = UUID.randomUUID();
        UUID userIdMock = UUID.randomUUID();

        doNothing().when(noteServiceImpl).deleteNote(userIdMock, noteIdMock);
        ResponseEntity<Void> response = noteController.deleteNote(userIdMock, noteIdMock);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void shouldGetNoteByPetCorrectly() {
        UUID petIdMock = UUID.randomUUID();
        Note noteMock = mock(Note.class);
        List<Note> noteList = List.of(noteMock);

        when(noteServiceImpl.getNoteByPet(petIdMock, 10)).thenReturn(noteList);

        ResponseEntity<List<Note>> response = noteController.getNoteByPet(petIdMock, 10);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(noteList, response.getBody());

        verify(noteServiceImpl).getNoteByPet(petIdMock, 10);
    }

    @Test
    void shouldGetNoteByUserCorrectly() {
        UUID userIdMock = UUID.randomUUID();
        Note noteMock = mock(Note.class);
        List<Note> noteList = List.of(noteMock);

        when(noteServiceImpl.getNoteByUser(userIdMock, 0)).thenReturn(noteList);

        ResponseEntity<List<Note>> response = noteController.getNoteByUser(userIdMock, 0);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(noteList, response.getBody());

        verify(noteServiceImpl).getNoteByUser(userIdMock, 0);
    }
}