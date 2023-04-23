package dev.vitorvidal.petmanagementapi.domain.repository;

import dev.vitorvidal.petmanagementapi.entity.NoteEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NoteRepository {
    Slice<NoteEntity> findByPetId(UUID petId, Pageable pageable);

    Slice<NoteEntity> findByUserId(UUID userId, Pageable pageable);
}
