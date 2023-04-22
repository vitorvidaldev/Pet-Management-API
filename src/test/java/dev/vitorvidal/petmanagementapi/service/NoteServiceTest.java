package dev.vitorvidal.petmanagementapi.service;

import dev.vitorvidal.petmanagementapi.domain.entity.NoteEntity;
import dev.vitorvidal.petmanagementapi.domain.model.CreateNote;
import dev.vitorvidal.petmanagementapi.domain.model.Note;
import dev.vitorvidal.petmanagementapi.domain.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
    void shouldGetNoteByIdCorrectly() {
        UUID noteIdMock = UUID.randomUUID();
        UUID userIdMock = UUID.randomUUID();
        NoteEntity noteEntityMock = mock(NoteEntity.class);
        Optional<NoteEntity> optionalNote = Optional.of(noteEntityMock);

        when(noteRepository.findById(noteIdMock)).thenReturn(optionalNote);
        when(optionalNote.get().getUserId()).thenReturn(userIdMock);

        Note note = noteService.getNoteById(userIdMock, noteIdMock);

        assertNotNull(note);
        verify(noteRepository, times(1)).findById(noteIdMock);
    }

    @Test
    void shouldThrowNotFoundExceptionGettingNote() {
        UUID noteIdMock = UUID.randomUUID();
        UUID userIdMock = UUID.randomUUID();

        when(noteRepository.findById(noteIdMock)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> noteService.getNoteById(userIdMock, noteIdMock));

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Note not found", exception.getReason());

        verify(noteRepository, times(1)).findById(noteIdMock);
    }

    @Test
    void shouldCreateNoteCorrectly() {
        CreateNote createNoteMock = mock(CreateNote.class);
        NoteEntity noteEntityMock = mock(NoteEntity.class);

        UUID noteIdMock = UUID.randomUUID();
        String noteTypeMock = "Test type";
        String noteTitleMock = "Test title";
        String noteDescriptionMock = "Test description";
        LocalDateTime noteCreationDateMock = LocalDateTime.now();
        UUID petIdMock = UUID.randomUUID();

        when(createNoteMock.noteType()).thenReturn(noteTypeMock);
        when(createNoteMock.noteTitle()).thenReturn(noteTitleMock);
        when(createNoteMock.description()).thenReturn(noteDescriptionMock);

        when(noteEntityMock.getNoteId()).thenReturn(noteIdMock);
        when(noteEntityMock.getNoteType()).thenReturn(noteTypeMock);
        when(noteEntityMock.getNoteTitle()).thenReturn(noteTitleMock);
        when(noteEntityMock.getNoteDescription()).thenReturn(noteDescriptionMock);
        when(noteEntityMock.getCreationDate()).thenReturn(noteCreationDateMock);
        when(noteEntityMock.getPetId()).thenReturn(petIdMock);

        when(noteRepository.save(any(NoteEntity.class))).thenReturn(noteEntityMock);

        Note note = noteService.createNote(createNoteMock);

        assertNotNull(note);
        assertEquals(noteIdMock, note.id());
        assertEquals(noteTypeMock, note.noteType());
        assertEquals(noteTitleMock, note.noteTitle());
        assertEquals(noteDescriptionMock, note.noteDescription());
        assertEquals(noteCreationDateMock, note.creationDate());
        assertEquals(petIdMock, note.petId());

        verify(noteRepository).save(any(NoteEntity.class));
    }

    @Test
    void shouldDeleteNoteCorrectly() {
        UUID noteIdMock = UUID.randomUUID();
        UUID userIdMock = UUID.randomUUID();
        NoteEntity noteEntityMock = mock(NoteEntity.class);
        Optional<NoteEntity> optionalNoteEntityMock = Optional.of(noteEntityMock);

        doNothing().when(noteRepository).deleteById(noteIdMock);
        when(noteRepository.findById(noteIdMock)).thenReturn(optionalNoteEntityMock);
        when(noteEntityMock.getUserId()).thenReturn(userIdMock);

        assertDoesNotThrow(() -> noteService.deleteNote(userIdMock, noteIdMock));
        verify(noteRepository).deleteById(noteIdMock);
    }

    @Test
    void shouldGetNoteByPetCorrectly() {
        UUID petIdMock = UUID.randomUUID();
        NoteEntity noteEntityMock = mock(NoteEntity.class);
        List<NoteEntity> noteEntityList = List.of(noteEntityMock);

        CassandraPageRequest pageRequestMock = CassandraPageRequest.first(10);
        SliceImpl<NoteEntity> noteEntitySliceMock = new SliceImpl<>(noteEntityList);

        UUID noteIdMock = UUID.randomUUID();
        String noteTypeMock = "Test type";
        String noteTitleMock = "Test title";
        String noteDescriptionMock = "Test description";
        LocalDateTime noteCreationDateMock = LocalDateTime.now();

        when(noteRepository.findByPetId(petIdMock, pageRequestMock)).thenReturn(noteEntitySliceMock);

        when(noteEntityMock.getNoteId()).thenReturn(noteIdMock);
        when(noteEntityMock.getNoteType()).thenReturn(noteTypeMock);
        when(noteEntityMock.getNoteTitle()).thenReturn(noteTitleMock);
        when(noteEntityMock.getNoteDescription()).thenReturn(noteDescriptionMock);
        when(noteEntityMock.getCreationDate()).thenReturn(noteCreationDateMock);
        when(noteEntityMock.getPetId()).thenReturn(petIdMock);

        List<Note> noteList = noteService.getNoteByPet(petIdMock, 10);

        assertNotNull(noteList);
        assertEquals(noteEntityList.size(), noteList.size());

        verify(noteRepository).findByPetId(petIdMock, pageRequestMock);
    }

    @Test
    void shouldGetNoteByUserCorrectly() {
        UUID userIdMock = UUID.randomUUID();
        NoteEntity noteEntityMock = mock(NoteEntity.class);
        List<NoteEntity> noteEntityList = List.of(noteEntityMock);

        CassandraPageRequest pageRequestMock = CassandraPageRequest.first(10);
        SliceImpl<NoteEntity> noteEntitySliceMock = new SliceImpl<>(noteEntityList);

        UUID noteIdMock = UUID.randomUUID();
        String noteTypeMock = "Test type";
        String noteTitleMock = "Test title";
        String noteDescriptionMock = "Test description";
        LocalDateTime noteCreationDateMock = LocalDateTime.now();

        when(noteRepository.findByUserId(userIdMock, pageRequestMock)).thenReturn(noteEntitySliceMock);

        when(noteEntityMock.getNoteId()).thenReturn(noteIdMock);
        when(noteEntityMock.getNoteType()).thenReturn(noteTypeMock);
        when(noteEntityMock.getNoteTitle()).thenReturn(noteTitleMock);
        when(noteEntityMock.getNoteDescription()).thenReturn(noteDescriptionMock);
        when(noteEntityMock.getCreationDate()).thenReturn(noteCreationDateMock);
        when(noteEntityMock.getUserId()).thenReturn(userIdMock);

        List<Note> noteList = noteService.getNoteByUser(userIdMock, 10);

        assertNotNull(noteList);
        assertEquals(noteEntityList.size(), noteList.size());

        verify(noteRepository).findByUserId(userIdMock, pageRequestMock);
    }

    @Test
    void shouldThrowNotFoundExceptionDeletingNote() {
        UUID userIdMock = UUID.randomUUID();
        UUID noteIdMock = UUID.randomUUID();

        when(noteRepository.findById(noteIdMock)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> noteService.deleteNote(userIdMock, noteIdMock)
        );

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Note not found", exception.getReason());

        verify(noteRepository).findById(noteIdMock);
    }
}