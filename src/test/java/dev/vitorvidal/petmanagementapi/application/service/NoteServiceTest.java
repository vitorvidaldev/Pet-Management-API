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

        NoteDTO note = noteService.getNoteById(userIdMock, noteIdMock);

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
        UUID userIdMock = UUID.randomUUID();
        NoteEntity noteEntityMock = mock(NoteEntity.class);
        Optional<NoteEntity> optionalNoteEntityMock = Optional.of(noteEntityMock);

        doNothing().when(noteRepository).deleteById(noteIdMock);
        when(noteRepository.findById(noteIdMock)).thenReturn(optionalNoteEntityMock);
        when(noteEntityMock.getUserId()).thenReturn(userIdMock);

        assertDoesNotThrow(() -> noteService.deleteNote(userIdMock, noteIdMock));
        verify(noteRepository, times(1)).deleteById(noteIdMock);
    }

    @Test
    void shouldGetNoteByPetCorrectly() {
        UUID petIdMock = UUID.randomUUID();
        NoteEntity noteEntityMock = mock(NoteEntity.class);
        List<NoteEntity> noteEntityList = List.of(noteEntityMock);
        Optional<List<NoteEntity>> optionalNoteEntityList = Optional.of(noteEntityList);

        UUID noteIdMock = UUID.randomUUID();
        String noteTypeMock = "Test type";
        String noteTitleMock = "Test title";
        String noteDescriptionMock = "Test description";
        LocalDateTime noteCreationDateMock = LocalDateTime.now();

        when(noteRepository.findByPetId(petIdMock)).thenReturn(optionalNoteEntityList);

        when(noteEntityMock.getNoteId()).thenReturn(noteIdMock);
        when(noteEntityMock.getNoteType()).thenReturn(noteTypeMock);
        when(noteEntityMock.getNoteTitle()).thenReturn(noteTitleMock);
        when(noteEntityMock.getNoteDescription()).thenReturn(noteDescriptionMock);
        when(noteEntityMock.getCreationDate()).thenReturn(noteCreationDateMock);
        when(noteEntityMock.getPetId()).thenReturn(petIdMock);

        List<NoteDTO> noteDTOList = noteService.getNoteByPet(petIdMock);

        assertNotNull(noteDTOList);
        assertEquals(noteEntityList.size(), noteDTOList.size());

        verify(noteRepository).findByPetId(petIdMock);
    }

    @Test
    void shouldGetNoteByUserCorrectly() {
        UUID userIdMock = UUID.randomUUID();
        NoteEntity noteEntityMock = mock(NoteEntity.class);
        List<NoteEntity> noteEntityList = List.of(noteEntityMock);
        Optional<List<NoteEntity>> optionalNoteEntityList = Optional.of(noteEntityList);

        UUID noteIdMock = UUID.randomUUID();
        String noteTypeMock = "Test type";
        String noteTitleMock = "Test title";
        String noteDescriptionMock = "Test description";
        LocalDateTime noteCreationDateMock = LocalDateTime.now();

        when(noteRepository.findByUserId(userIdMock)).thenReturn(optionalNoteEntityList);

        when(noteEntityMock.getNoteId()).thenReturn(noteIdMock);
        when(noteEntityMock.getNoteType()).thenReturn(noteTypeMock);
        when(noteEntityMock.getNoteTitle()).thenReturn(noteTitleMock);
        when(noteEntityMock.getNoteDescription()).thenReturn(noteDescriptionMock);
        when(noteEntityMock.getCreationDate()).thenReturn(noteCreationDateMock);
        when(noteEntityMock.getUserId()).thenReturn(userIdMock);

        List<NoteDTO> noteDTOList = noteService.getNoteByUser(userIdMock);

        assertNotNull(noteDTOList);
        assertEquals(noteEntityList.size(), noteDTOList.size());

        verify(noteRepository).findByUserId(userIdMock);
    }

    @Test
    void shouldThrowNotFoundExceptionGettingNotesByUserId() {
        UUID userIdMock = UUID.randomUUID();

        when(noteRepository.findByUserId(userIdMock)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> noteService.getNoteByUser(userIdMock)
        );

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Notes not found", exception.getReason());

        verify(noteRepository).findByUserId(userIdMock);
    }

    @Test
    void shouldThrowNotFoundExceptionGettingNotesByPetId() {
        UUID petIdMock = UUID.randomUUID();

        when(noteRepository.findByPetId(petIdMock)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> noteService.getNoteByPet(petIdMock)
        );

        assertNotNull(exception);
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Notes not found", exception.getReason());

        verify(noteRepository).findByPetId(petIdMock);
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
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Note not found", exception.getReason());

        verify(noteRepository).findById(noteIdMock);
    }
}