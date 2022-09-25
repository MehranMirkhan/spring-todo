package com.fpt.todo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TodoRepo extends JpaRepository<Todo, UUID> {
    Optional<Todo> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);
}
