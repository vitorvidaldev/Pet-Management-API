package dev.vitorvidal.petmanagementapi.application.controller;

import dev.vitorvidal.petmanagementapi.application.service.NoteService;
import dev.vitorvidal.petmanagementapi.model.dto.CreateNoteDTO;
import dev.vitorvidal.petmanagementapi.model.dto.ErrorResponseDTO;
import dev.vitorvidal.petmanagementapi.model.dto.NoteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/v1/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Operation(summary = "Get note data")
    @ApiResponse(responseCode = "200", description = "Returns note data",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = NoteDTO.class))})
    @ApiResponse(responseCode = "404", description = "Note not found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class))})
    @GetMapping("/user/{userId}/note/{noteId}")
    public ResponseEntity<NoteDTO> getNoteById(
            @PathVariable(value = "userId") UUID userId,
            @PathVariable(value = "noteId") UUID noteId) {
        NoteDTO note = noteService.getNoteById(userId, noteId);
        return ResponseEntity.ok().body(note);
    }

    @Operation(summary = "Get users notes data")
    @ApiResponse(responseCode = "200", description = "Returns users note data",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = List.class))})
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NoteDTO>> getNoteByUser(
            @PathVariable(value = "userId") UUID userId,
            @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize) {
        List<NoteDTO> noteDTOList = noteService.getNoteByUser(userId, pageSize);
        return ResponseEntity.ok().body(noteDTOList);
    }

    @Operation(summary = "Get pets notes data")
    @ApiResponse(responseCode = "200", description = "Returns pets note data",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = List.class))})
    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<NoteDTO>> getNoteByPet(
            @PathVariable(value = "petId") UUID petId,
            @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize) {
        List<NoteDTO> noteDTOList = noteService.getNoteByPet(petId, pageSize);
        return ResponseEntity.ok().body(noteDTOList);
    }

    @Operation(summary = "Create note")
    @ApiResponse(responseCode = "200", description = "Returns note data",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = NoteDTO.class))})
    @PostMapping
    public ResponseEntity<NoteDTO> createNote(
            @RequestBody @Valid CreateNoteDTO createNoteDTO) {
        NoteDTO note = noteService.createNote(createNoteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(note);
    }

    @Operation(summary = "Deletes note")
    @ApiResponse(responseCode = "204", description = "Deletes note",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema())})
    @ApiResponse(responseCode = "404", description = "Note not found",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class))})
    @DeleteMapping("/user/{userId}/note/{noteId}")
    public ResponseEntity<Void> deleteNote(
            @PathVariable(value = "userId") UUID userId,
            @PathVariable(value = "noteId") UUID noteId) {
        noteService.deleteNote(userId, noteId);
        return ResponseEntity.noContent().build();
    }
}
