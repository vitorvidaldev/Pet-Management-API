package dev.vitorvidal.petmanagementapi.model.repository;

import dev.vitorvidal.petmanagementapi.model.entity.PetEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PetRepository extends CassandraRepository<PetEntity, UUID> {
    Slice<PetEntity> findByUserId(UUID userId, Pageable pageable);
}
