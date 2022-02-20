package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.model.dto.CreateNoteDTO;
import dev.vitorvidal.petmanagementapi.model.dto.NoteDTO;
import dev.vitorvidal.petmanagementapi.model.entity.NoteEntity;
import dev.vitorvidal.petmanagementapi.model.repository.NoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteDTO> getAllNotes() {
        List<NoteEntity> noteList = noteRepository.findAll();

        List<NoteDTO> noteDTOList = new ArrayList<>();
        for (NoteEntity noteEntity : noteList) {
            noteDTOList.add(new NoteDTO(
                    noteEntity.getNoteId(),
                    noteEntity.getNoteType(),
                    noteEntity.getNoteTitle(),
                    noteEntity.getNoteDescription(),
                    noteEntity.getCreationDate(),
                    noteEntity.getPetId()
            ));
        }
        return noteDTOList;
    }

    public NoteDTO getNoteById(UUID noteId) {
        Optional<NoteEntity> optionalNote = noteRepository.findById(noteId);

        if (optionalNote.isPresent()) {
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

    public void deleteNote(UUID noteId) {
        noteRepository.deleteById(noteId);
    }
}
