package dev.vitorvidal.petmanagementapi.domain.service.impl;

import dev.vitorvidal.petmanagementapi.domain.model.CreateNote;
import dev.vitorvidal.petmanagementapi.domain.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NoteServiceImpl {

    public Note getNoteById(UUID userId, UUID noteId) {
        return null;
    }

    public Note createNote(CreateNote createNote) {
        return null;
    }

    public void deleteNote(UUID userId, UUID noteId) {
    }

    public List<Note> getNoteByPet(UUID petId, int pageSize) {
        return null;
    }

    public List<Note> getNoteByUser(UUID userId, int pageSize) {
        return null;
    }
}
