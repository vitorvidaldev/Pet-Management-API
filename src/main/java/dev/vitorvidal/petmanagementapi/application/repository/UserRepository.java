package dev.vitorvidal.petmanagementapi.application.repository;

import dev.vitorvidal.petmanagementapi.model.user.UserEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CassandraRepository<UserEntity, String> {
    void deleteByUserId(UUID id);

    Optional<UserEntity> findByUserId(UUID id);
}