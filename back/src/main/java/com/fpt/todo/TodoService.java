package com.fpt.todo;

import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TodoService {
    Page<TodoDTO> findAll(TodoSearchDTO example, Pageable pageable);

    TodoDTO get(UUID id);

    TodoDTO create(TodoCreateDTO dto);

    TodoDTO update(UUID id, TodoUpdateDTO dto);

    void delete(UUID id);

    @Builder
    record TodoDTO(UUID id, String text, boolean done) {
        static TodoDTO fromEntity(Todo entity) {
            return TodoDTO.builder()
                    .id(entity.getId())
                    .text(entity.getText())
                    .done(entity.isDone())
                    .build();
        }
    }

    record TodoCreateDTO(String text) {
    }

    record TodoSearchDTO(String text, Boolean done) {
        Todo toEntity() {
            return Todo.builder().text(text).done(done != null && done).build();
        }
    }

    record TodoUpdateDTO(String text, boolean done) {
    }
}
