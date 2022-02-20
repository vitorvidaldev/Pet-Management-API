package dev.vitorvidal.petmanagementapi.application.repository;

import dev.vitorvidal.petmanagementapi.model.entity.NoteEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NoteRepository extends CassandraRepository<NoteEntity, UUID> {
}
