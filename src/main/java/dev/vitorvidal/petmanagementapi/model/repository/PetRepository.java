package dev.vitorvidal.petmanagementapi.model.repository;

import dev.vitorvidal.petmanagementapi.model.entity.PetEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PetRepository extends CassandraRepository<PetEntity, UUID> {
    Optional<List<PetEntity>> findByUserId(UUID userId);
}
