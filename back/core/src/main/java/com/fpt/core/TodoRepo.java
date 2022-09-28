package com.fpt.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TodoRepo extends JpaRepository<Todo, UUID> {
    Optional<Todo> findByUuid(UUID uuid);

    void deleteByUuid(UUID uuid);

    @Modifying
    @Query("update Todo t set t.done = :done where t.uuid = :uuid")
    void setDone(@Param("uuid") UUID uuid, @Param("done") boolean done);
}
