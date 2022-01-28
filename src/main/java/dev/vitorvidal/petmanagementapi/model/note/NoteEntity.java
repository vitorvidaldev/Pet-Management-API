package dev.vitorvidal.petmanagementapi.model.note;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(value = "note")
public class NoteEntity {
    @PrimaryKey(value = "note_id")
    private UUID noteId;
    @Column(value = "note_type")
    private String noteType;
    @Column(value = "note_title")
    private String noteTitle;
    @Column(value = "note_description")
    private String noteDescription;
    @Column(value = "creation_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private LocalDateTime creationDate;
    @Column(value = "pet_id")
    private UUID petId;

    public NoteEntity(String noteType, String noteTitle, String noteDescription) {
        this.noteType = noteType;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }

    public UUID getNoteId() {
        return noteId;
    }

    public void setNoteId(UUID noteId) {
        this.noteId = noteId;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public UUID getPetId() {
        return petId;
    }

    public void setPetId(UUID petId) {
        this.petId = petId;
    }
}
