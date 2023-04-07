package dev.vitorvidal.petmanagementapi.application.controller;

import dev.vitorvidal.petmanagementapi.application.service.NoteService;
import dev.vitorvidal.petmanagementapi.model.dto.CreateNoteDTO;
import dev.vitorvidal.petmanagementapi.model.dto.NoteDTO;
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
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable(value = "userId") UUID userId,
                                               @PathVariable(value = "noteId") UUID noteId) {
        NoteDTO note = noteService.getNoteById(userId, noteId);
        return ResponseEntity.ok().body(note);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NoteDTO>> getNoteByUser(
            @PathVariable(value = "userId") UUID userId,
            @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize) {
        List<NoteDTO> noteDTOList = noteService.getNoteByUser(userId, pageSize);
        return ResponseEntity.ok().body(noteDTOList);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<NoteDTO>> getNoteByPet(
            @PathVariable(value = "petId") UUID petId,
            @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize) {
        List<NoteDTO> noteDTOList = noteService.getNoteByPet(petId, pageSize);
        return ResponseEntity.ok().body(noteDTOList);
    }

    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@RequestBody @Valid CreateNoteDTO createNoteDTO) {
        NoteDTO note = noteService.createNote(createNoteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(note);
    }

    @DeleteMapping("/user/{userId}/note/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable(value = "userId") UUID userId, @PathVariable(value = "noteId") UUID noteId) {
        noteService.deleteNote(userId, noteId);
        return ResponseEntity.noContent().build();
    }
}
