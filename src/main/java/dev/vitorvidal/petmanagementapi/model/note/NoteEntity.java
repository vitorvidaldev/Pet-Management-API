package dev.vitorvidal.petmanagementapi.model.note;

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
    private LocalDateTime creationDate;
    @Column(value = "pet_id")
    private UUID petId;
}
