package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.application.repository.NoteRepository;
import dev.vitorvidal.petmanagementapi.model.note.CreateNoteDTO;
import dev.vitorvidal.petmanagementapi.model.note.NoteDTO;
import dev.vitorvidal.petmanagementapi.model.note.NoteEntity;
import org.springframework.stereotype.Service;

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

    public List<NoteDTO> listNotes() {
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

    public NoteDTO getNoteById(UUID id) {
        Optional<NoteEntity> optionalNote = noteRepository.findById(id);

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
        return null;
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

    public void deleteNote(UUID id) {
        noteRepository.deleteById(id);
    }
}
