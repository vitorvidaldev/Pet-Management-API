package dev.vitorvidal.petmanagementapi.model.repository;

import dev.vitorvidal.petmanagementapi.model.entity.UserEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CassandraRepository<UserEntity, String> {
    Optional<UserEntity> findByUserId(UUID id);
}