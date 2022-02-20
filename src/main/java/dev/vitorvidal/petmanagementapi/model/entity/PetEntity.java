package dev.vitorvidal.petmanagementapi.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Table(value = "pet")
public class PetEntity {
    @PrimaryKey(value = "pet_id")
    private UUID petId;
    @Column
    private String name;
    @Column
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private LocalDateTime birthDate;
    @Column
    private String species; // TODO change to enum
    @Column
    private String breed; // TODO change to enum
    @Column(value = "creation_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private LocalDateTime creationDate;
    @Column(value = "user_id")
    @Indexed
    private UUID userId;

    public PetEntity() {
    }

    public PetEntity(
            UUID petId,
            String name,
            LocalDateTime birthDate,
            String species,
            String breed,
            LocalDateTime creationDate,
            UUID userId) {
        this.petId = petId;
        this.name = name;
        this.birthDate = birthDate;
        this.species = species;
        this.breed = breed;
        this.creationDate = creationDate;
        this.userId = userId;
    }

    public PetEntity(
            String name,
            LocalDateTime birthDate,
            String species,
            String breed,
            UUID userId
    ) {
        this.petId = UUID.randomUUID();
        this.name = name;
        this.birthDate = birthDate;
        this.species = species;
        this.breed = breed;
        this.userId = userId;
        this.creationDate = LocalDateTime.now();
    }
}
