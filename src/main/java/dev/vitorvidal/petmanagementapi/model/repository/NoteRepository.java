package dev.vitorvidal.petmanagementapi.model.repository;

import dev.vitorvidal.petmanagementapi.model.entity.NoteEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NoteRepository extends CassandraRepository<NoteEntity, UUID> {
    Slice<NoteEntity> findByPetId(UUID petId, Pageable pageable);

    Slice<NoteEntity> findByUserId(UUID userId, Pageable pageable);
}
