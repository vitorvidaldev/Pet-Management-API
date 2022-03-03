package dev.vitorvidal.petmanagementapi.model.repository;

import dev.vitorvidal.petmanagementapi.model.entity.NoteEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteRepository extends CassandraRepository<NoteEntity, UUID> {

    Optional<List<NoteEntity>> findByPetId(UUID petId);

    Optional<List<NoteEntity>> findByUserId(UUID userId);
}
