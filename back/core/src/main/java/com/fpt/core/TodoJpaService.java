package com.fpt.core;

import com.fpt.common.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoJpaService implements TodoService {
    private final        TodoRepo repo;
    private static final String   NOT_FOUND_MSG = "Todo item not found";

    //region CRUD

    @Override
    public Page<TodoDTO> findAll() {
        return findAll(Pageable.unpaged());
    }

    @Override
    public Page<TodoDTO> findAll(Pageable pageable) {
        return findAll(TodoSearchDTO.builder().build(), pageable);
    }

    @Override
    public Page<TodoDTO> findAll(TodoSearchDTO example, Pageable pageable) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                                               .withIgnoreCase()
                                               .withIgnoreNullValues()
                                               .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                                               .withIgnorePaths("id", "uuid", "version");
        return repo.findAll(Example.of(example.toEntity(), matcher), pageable)
                   .map(TodoDTO::fromEntity);
    }

    @Override
    public TodoDTO getByUUID(UUID uuid) {
        return repo.findByUuid(uuid)
                   .map(TodoDTO::fromEntity)
                   .orElseThrow(() -> new NotFoundException(NOT_FOUND_MSG, Map.of("uuid", uuid)));
    }

    @Override
    public TodoDTO create(TodoCreateDTO dto) {
        Todo entity = Todo.builder()
                          .text(dto.text())
                          .done(false)
                          .build();
        return TodoDTO.fromEntity(repo.save(entity));
    }

    @Override
    public TodoDTO update(UUID uuid, TodoUpdateDTO dto) {
        Todo updatedEntity = repo.findByUuid(uuid)
                                 .map(entity -> {
                                     entity.setText(dto.text());
                                     entity.setDone(dto.done());
                                     return entity;
                                 }).orElseThrow(() -> new NotFoundException(NOT_FOUND_MSG,
                                                                            Map.of("uuid", uuid)));
        return TodoDTO.fromEntity(repo.save(updatedEntity));
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        repo.deleteByUuid(uuid);
    }

    @Override
    public void deleteAll() {
        repo.deleteAll();
    }

    //endregion

    //region Features

    @Override
    @Transactional
    public TodoService.TodoDTO done(UUID uuid) {
        repo.setDone(uuid, true);
        Todo updatedEntity = repo.findByUuid(uuid)
                                 .orElseThrow(() -> new NotFoundException(NOT_FOUND_MSG,
                                                                          Map.of("uuid", uuid)));
        return TodoDTO.fromEntity(repo.save(updatedEntity));
    }

    @Override
    @Transactional
    public TodoService.TodoDTO undone(UUID uuid) {
        repo.setDone(uuid, false);
        Todo updatedEntity = repo.findByUuid(uuid)
                                 .orElseThrow(() -> new NotFoundException(NOT_FOUND_MSG,
                                                                          Map.of("uuid", uuid)));
        return TodoDTO.fromEntity(repo.save(updatedEntity));
    }

    //endregion
}
