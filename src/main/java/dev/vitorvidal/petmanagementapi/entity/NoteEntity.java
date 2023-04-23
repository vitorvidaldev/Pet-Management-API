package dev.vitorvidal.petmanagementapi.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class NoteEntity {
    private UUID noteId;
    private String noteType;
    private String noteTitle;
    private String noteDescription;
    private LocalDateTime creationDate;
    private UUID petId;
    private UUID userId;

    public NoteEntity(String noteType, String noteTitle, String noteDescription, UUID userId, UUID petId) {
        this.noteId = UUID.randomUUID();
        this.noteType = noteType;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userId = userId;
        this.petId = petId;
        this.creationDate = LocalDateTime.now();
    }
}
