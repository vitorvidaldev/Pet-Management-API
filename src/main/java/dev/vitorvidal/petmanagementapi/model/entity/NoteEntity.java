package dev.vitorvidal.petmanagementapi.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
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
    @Indexed
    private UUID petId;
    @Column(value = "user_id")
    @Indexed
    private UUID userId;

    public NoteEntity(String noteType, String noteTitle, String noteDescription) {
        this.noteType = noteType;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
    }
}
