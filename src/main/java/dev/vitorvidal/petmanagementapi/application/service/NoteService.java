package dev.vitorvidal.petmanagementapi.application.service;

import dev.vitorvidal.petmanagementapi.model.repository.NoteRepository;
import dev.vitorvidal.petmanagementapi.model.dto.CreateNoteDTO;
import dev.vitorvidal.petmanagementapi.model.dto.NoteDTO;
import dev.vitorvidal.petmanagementapi.model.entity.NoteEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteDTO> listNotes() {
        List<NoteEntity> noteList = noteRepository.findAll();

        List<NoteDTO> noteDTOList = new ArrayList<>();
        for (NoteEntity noteEntity : noteList) {
            noteDTOList.add(modelMapper.map(noteEntity, NoteDTO.class));
        }
        return noteDTOList;
    }

    public NoteDTO getNoteById(UUID id) {
        Optional<NoteEntity> optionalNote = noteRepository.findById(id);

        if (optionalNote.isPresent()) {
            NoteEntity noteEntity = optionalNote.get();
            return modelMapper.map(noteEntity, NoteDTO.class);
        }
        return null;
    }

    public NoteDTO createNote(CreateNoteDTO createNoteDTO) {
        NoteEntity noteEntity = noteRepository.save(modelMapper.map(createNoteDTO, NoteEntity.class));
        return modelMapper.map(noteEntity, NoteDTO.class);
    }

    public void deleteNote(UUID id) {
        noteRepository.deleteById(id);
    }
}
