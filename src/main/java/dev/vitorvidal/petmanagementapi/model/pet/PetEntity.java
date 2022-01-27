package dev.vitorvidal.petmanagementapi.model.pet;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(value = "pet")
public class PetEntity {
    @PrimaryKey(value = "pet_id")
    private UUID petId;
    @Column
    private String name;
    @Column
    private LocalDateTime birthDate;
    @Column
    private String species; // TODO change to enum
    @Column
    private String breed; // TODO change to enum
    @Column
    private LocalDateTime creationDate;
    @Column(value = "user_id")
    private UUID userId;
}
