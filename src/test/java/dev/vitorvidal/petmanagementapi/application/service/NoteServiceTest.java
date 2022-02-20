package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.model.dto.CreateNoteDTO;
import dev.vitorvidal.petmanagementapi.model.dto.NoteDTO;
import dev.vitorvidal.petmanagementapi.model.entity.NoteEntity;
import dev.vitorvidal.petmanagementapi.model.repository.NoteRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class NoteServiceTest {
    @InjectMocks
    private NoteService noteService;
    @Mock
    private NoteRepository noteRepository;

    @Test
    void shouldListNotesCorrectly() {
        NoteEntity noteEntityMock = mock(NoteEntity.class);
        List<NoteEntity> noteEntityList = List.of(noteEntityMock);

        when(noteRepository.findAll()).thenReturn(noteEntityList);
        List<NoteDTO> notes = noteService.getAllNotes();

        assertNotNull(notes);
        assertEquals(noteEntityList.size(), notes.size());
    }

    @Test
    void shouldGetNoteByIdCorrectly() {
        UUID noteId = UUID.randomUUID();
        NoteEntity noteEntityMock = mock(NoteEntity.class);
        Optional<NoteEntity> optionalNote = Optional.of(noteEntityMock);

        when(noteRepository.findById(noteId)).thenReturn(optionalNote);

        NoteDTO note = noteService.getNoteById(noteId);

        assertNotNull(note);
    }

    @Test
    void shouldThrowNotFoundExceptionGettingNote() {
        UUID noteId = UUID.randomUUID();

        when(noteRepository.findById(noteId)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> noteService.getNoteById(noteId));

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Note not found", exception.getReason());
    }

    @Test
    @Disabled("How to test create methods?")
    void shouldCreateNoteCorrectly() {
        CreateNoteDTO createNoteDTOMock = mock(CreateNoteDTO.class);
        NoteEntity noteEntityMock = mock(NoteEntity.class);

        when(noteRepository.save(noteEntityMock)).thenReturn(noteEntityMock);
        NoteDTO note = noteService.createNote(createNoteDTOMock);

        assertNotNull(note);
    }

    @Test
    void shouldDeleteNoteCorrectly() {
        UUID noteId = UUID.randomUUID();
        doNothing().when(noteRepository).deleteById(noteId);
        assertDoesNotThrow(() -> noteService.deleteNote(noteId));
    }
}