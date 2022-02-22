package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.model.dto.CreateNoteDTO;
import dev.vitorvidal.petmanagementapi.model.dto.NoteDTO;
import dev.vitorvidal.petmanagementapi.model.entity.NoteEntity;
import dev.vitorvidal.petmanagementapi.model.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
    void shouldGetNoteByIdCorrectly() {
        UUID noteIdMock = UUID.randomUUID();
        NoteEntity noteEntityMock = mock(NoteEntity.class);
        Optional<NoteEntity> optionalNote = Optional.of(noteEntityMock);

        when(noteRepository.findById(noteIdMock)).thenReturn(optionalNote);

        NoteDTO note = noteService.getNoteById(noteIdMock);

        assertNotNull(note);
        verify(noteRepository, times(1)).findById(noteIdMock);
    }

    @Test
    void shouldThrowNotFoundExceptionGettingNote() {
        UUID noteIdMock = UUID.randomUUID();

        when(noteRepository.findById(noteIdMock)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> noteService.getNoteById(noteIdMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Note not found", exception.getReason());

        verify(noteRepository, times(1)).findById(noteIdMock);
    }

    @Test
    void shouldCreateNoteCorrectly() {
        CreateNoteDTO createNoteDTOMock = mock(CreateNoteDTO.class);
        NoteEntity noteEntityMock = mock(NoteEntity.class);

        UUID noteIdMock = UUID.randomUUID();
        String noteTypeMock = "Test type";
        String noteTitleMock = "Test title";
        String noteDescriptionMock = "Test description";
        LocalDateTime noteCreationDateMock = LocalDateTime.now();
        UUID petIdMock = UUID.randomUUID();

        when(createNoteDTOMock.noteType()).thenReturn(noteTypeMock);
        when(createNoteDTOMock.noteTitle()).thenReturn(noteTitleMock);
        when(createNoteDTOMock.description()).thenReturn(noteDescriptionMock);

        when(noteEntityMock.getNoteId()).thenReturn(noteIdMock);
        when(noteEntityMock.getNoteType()).thenReturn(noteTypeMock);
        when(noteEntityMock.getNoteTitle()).thenReturn(noteTitleMock);
        when(noteEntityMock.getNoteDescription()).thenReturn(noteDescriptionMock);
        when(noteEntityMock.getCreationDate()).thenReturn(noteCreationDateMock);
        when(noteEntityMock.getPetId()).thenReturn(petIdMock);

        when(noteRepository.save(any(NoteEntity.class))).thenReturn(noteEntityMock);

        NoteDTO noteDTO = noteService.createNote(createNoteDTOMock);

        assertNotNull(noteDTO);
        assertEquals(noteIdMock, noteDTO.id());
        assertEquals(noteTypeMock, noteDTO.noteType());
        assertEquals(noteTitleMock, noteDTO.noteTitle());
        assertEquals(noteDescriptionMock, noteDTO.noteDescription());
        assertEquals(noteCreationDateMock, noteDTO.creationDate());
        assertEquals(petIdMock, noteDTO.petId());

        verify(noteRepository, times(1)).save(any(NoteEntity.class));
    }

    @Test
    void shouldDeleteNoteCorrectly() {
        UUID noteIdMock = UUID.randomUUID();
        doNothing().when(noteRepository).deleteById(noteIdMock);

        assertDoesNotThrow(() -> noteService.deleteNote(noteIdMock));
        verify(noteRepository, times(1)).deleteById(noteIdMock);
    }
}