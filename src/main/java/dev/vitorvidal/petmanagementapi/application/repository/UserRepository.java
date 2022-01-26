package dev.vitorvidal.petmanagementapi.application.repository;

import dev.vitorvidal.petmanagementapi.model.user.UserEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CassandraRepository<UserEntity, String> {
}
