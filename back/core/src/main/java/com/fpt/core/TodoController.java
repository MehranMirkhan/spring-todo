package com.fpt.core;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService service;

    //region CRUD

    @GetMapping
    public Page<TodoService.TodoDTO> findAll(TodoService.TodoSearchDTO example, Pageable pageable) {
        return service.findAll(example, pageable);
    }

    @GetMapping("{uuid}")
    public TodoService.TodoDTO getByUuid(@PathVariable UUID uuid) {
        return service.getByUUID(uuid);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TodoService.TodoDTO create(@RequestBody TodoService.TodoCreateDTO dto) {
        return service.create(dto);
    }

    @PutMapping("{uuid}")
    public TodoService.TodoDTO update(@PathVariable UUID uuid, @RequestBody TodoService.TodoUpdateDTO dto) {
        return service.update(uuid, dto);
    }

    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {
        service.delete(uuid);
    }

    //endregion

    //region Feature
    @PutMapping("{uuid}/done")
    public TodoService.TodoDTO done(@PathVariable UUID uuid) {
        return service.done(uuid);
    }

    @PutMapping("{uuid}/undone")
    public TodoService.TodoDTO undone(@PathVariable UUID uuid) {
        return service.undone(uuid);
    }
    //endregion
}
