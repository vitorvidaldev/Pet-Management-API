package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.model.dto.CreateNoteDTO;
import dev.vitorvidal.petmanagementapi.model.dto.NoteDTO;
import dev.vitorvidal.petmanagementapi.model.entity.NoteEntity;
import dev.vitorvidal.petmanagementapi.model.repository.NoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public NoteDTO getNoteById(UUID userId, UUID noteId) {
        Optional<NoteEntity> optionalNote = noteRepository.findById(noteId);

        if (optionalNote.isPresent() && optionalNote.get().getUserId().equals(userId)) {
            NoteEntity noteEntity = optionalNote.get();
            return new NoteDTO(
                    noteEntity.getNoteId(),
                    noteEntity.getNoteType(),
                    noteEntity.getNoteTitle(),
                    noteEntity.getNoteDescription(),
                    noteEntity.getCreationDate(),
                    noteEntity.getPetId()
            );
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Note not found");
    }

    public NoteDTO createNote(CreateNoteDTO createNoteDTO) {
        NoteEntity noteEntity = noteRepository.save(new NoteEntity(
                createNoteDTO.noteType(),
                createNoteDTO.noteTitle(),
                createNoteDTO.description()
        ));
        return new NoteDTO(
                noteEntity.getNoteId(),
                noteEntity.getNoteType(),
                noteEntity.getNoteTitle(),
                noteEntity.getNoteDescription(),
                noteEntity.getCreationDate(),
                noteEntity.getPetId()
        );
    }

    public void deleteNote(UUID userId, UUID noteId) {
        Optional<NoteEntity> optionalNote = noteRepository.findById(noteId);

        if (optionalNote.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found");
        }

        NoteEntity noteEntity = optionalNote.get();

        if (noteEntity.getUserId().equals(userId)) {
            noteRepository.deleteById(noteId);
        }
    }

    public List<NoteDTO> getNoteByPet(UUID petId) {
        return null;
    }

    public List<NoteDTO> getNoteByUser(UUID userId) {
        return null;
    }

}
