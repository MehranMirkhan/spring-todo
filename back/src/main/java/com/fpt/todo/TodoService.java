package com.fpt.todo;

import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TodoService {
    Page<TodoDTO> findAll();

    Page<TodoDTO> findAll(Pageable pageable);

    Page<TodoDTO> findAll(TodoSearchDTO example, Pageable pageable);

    TodoDTO getByUUID(UUID uuid);

    TodoDTO create(TodoCreateDTO dto);

    TodoDTO update(UUID uuid, TodoUpdateDTO dto);

    void delete(UUID uuid);

    void deleteAll();

    @Builder
    record TodoDTO(UUID uuid, String text, Boolean done) {
        static TodoDTO fromEntity(Todo entity) {
            return TodoDTO.builder()
                          .uuid(entity.getUuid())
                          .text(entity.getText())
                          .done(entity.getDone())
                          .build();
        }
    }

    @Builder
    record TodoCreateDTO(String text) {
    }

    @Builder
    record TodoSearchDTO(String text, Boolean done) {
        Todo toEntity() {
            return Todo.builder().text(text).done(done != null && done).build();
        }
    }

    @Builder
    record TodoUpdateDTO(String text, Boolean done) {
    }
}
