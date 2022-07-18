package com.fpt.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoJpaService implements TodoService {
    private final TodoRepo repo;

    @Override
    public Page<TodoDTO> findAll(TodoSearchDTO example, Pageable pageable) {
//        if (example == null)
//            return repo.findAll(pageable).map(TodoDTO::fromEntity);
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        return repo.findAll(Example.of(example.toEntity(), matcher), pageable)
                .map(TodoDTO::fromEntity);
    }

    @Override
    public TodoDTO get(UUID id) {
        return repo.findById(id)
                .map(TodoDTO::fromEntity)
                .orElseThrow(() -> new RuntimeException("Todo item with ID %s not found".formatted(id)));
    }

    @Override
    public TodoDTO create(TodoCreateDTO dto) {
        Todo entity = Todo.builder()
                .id(UUID.randomUUID())
                .text(dto.text())
                .done(false)
                .build();
        return TodoDTO.fromEntity(repo.save(entity));
    }

    @Override
    public TodoDTO update(UUID id, TodoUpdateDTO dto) {
        Todo updatedEntity = repo.findById(id)
                .map(entity -> {
                    entity.setText(dto.text());
                    entity.setDone(dto.done());
                    return entity;
                }).orElseThrow(() -> new RuntimeException("Todo item with ID %s not found".formatted(id)));
        return TodoDTO.fromEntity(repo.save(updatedEntity));
    }

    @Override
    public void delete(UUID id) {
        repo.deleteById(id);
    }
}