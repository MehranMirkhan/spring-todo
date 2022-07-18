package com.fpt.todo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TodoRepo extends JpaRepository<Todo, UUID> {
}
