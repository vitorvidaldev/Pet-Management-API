package dev.vitorvidal.petmanagementapi.model.pet;

import org.springframework.data.cassandra.core.mapping.CassandraType;
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
    private UUID userId;

    public PetEntity(String name, LocalDateTime birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public UUID getPetId() {
        return petId;
    }

    public void setPetId(UUID petId) {
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
