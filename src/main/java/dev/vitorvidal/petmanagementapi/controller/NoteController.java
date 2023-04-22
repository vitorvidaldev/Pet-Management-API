package dev.vitorvidal.petmanagementapi.controller;

import dev.vitorvidal.petmanagementapi.domain.model.CreateNote;
import dev.vitorvidal.petmanagementapi.domain.model.Note;
import dev.vitorvidal.petmanagementapi.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/v1/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/user/{userId}/note/{noteId}")
    public ResponseEntity<Note> getNoteById(@PathVariable(value = "userId") UUID userId,
                                            @PathVariable(value = "noteId") UUID noteId) {
        Note note = noteService.getNoteById(userId, noteId);
        return ResponseEntity.ok().body(note);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Note>> getNoteByUser(
            @PathVariable(value = "userId") UUID userId,
            @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize) {
        List<Note> noteList = noteService.getNoteByUser(userId, pageSize);
        return ResponseEntity.ok().body(noteList);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<Note>> getNoteByPet(
            @PathVariable(value = "petId") UUID petId,
            @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize) {
        List<Note> noteList = noteService.getNoteByPet(petId, pageSize);
        return ResponseEntity.ok().body(noteList);
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody @Valid CreateNote createNote) {
        Note note = noteService.createNote(createNote);
        return ResponseEntity.status(HttpStatus.CREATED).body(note);
    }

    @DeleteMapping("/user/{userId}/note/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable(value = "userId") UUID userId, @PathVariable(value = "noteId") UUID noteId) {
        noteService.deleteNote(userId, noteId);
        return ResponseEntity.noContent().build();
    }
}
