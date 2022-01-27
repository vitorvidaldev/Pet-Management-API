package dev.vitorvidal.petmanagementapi.application.controller;

import dev.vitorvidal.petmanagementapi.application.service.NoteService;
import dev.vitorvidal.petmanagementapi.model.note.CreateNoteDTO;
import dev.vitorvidal.petmanagementapi.model.note.NoteDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/v1/note")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<NoteDTO>> listNotes() {
        List<NoteDTO> noteList = noteService.listNotes();
        return ResponseEntity.ok().body(noteList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable(value = "id") UUID id) {
        NoteDTO note = noteService.getNoteById(id);
        return ResponseEntity.ok().body(note);
    }

    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@RequestBody @Valid CreateNoteDTO createNoteDTO) {
        NoteDTO note = noteService.createNote(createNoteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(note);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable(value = "id") UUID id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
