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
        Optional<List<NoteEntity>> optionalNoteEntityList = noteRepository.findByPetId(petId);

        return mapNoteListToDTO(optionalNoteEntityList);
    }

    public List<NoteDTO> getNoteByUser(UUID userId) {
        Optional<List<NoteEntity>> optionalNoteEntityList = noteRepository.findByUserId(userId);

        return mapNoteListToDTO(optionalNoteEntityList);
    }

    private List<NoteDTO> mapNoteListToDTO(Optional<List<NoteEntity>> optionalNoteEntityList) {
        if (optionalNoteEntityList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notes not found");
        }

        List<NoteEntity> noteEntityList = optionalNoteEntityList.get();
        List<NoteDTO> noteDTOList = new ArrayList<>();
        for (NoteEntity noteEntity: noteEntityList) {
            NoteDTO noteDTO = new NoteDTO(
                    noteEntity.getNoteId(),
                    noteEntity.getNoteType(),
                    noteEntity.getNoteTitle(),
                    noteEntity.getNoteDescription(),
                    noteEntity.getCreationDate(),
                    noteEntity.getPetId()
            );
            noteDTOList.add(noteDTO);
        }
        return noteDTOList;
    }
}
