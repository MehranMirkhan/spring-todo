package com.fpt.todo;

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

    @GetMapping
    public Page<TodoService.TodoDTO> findAll(TodoService.TodoSearchDTO example, Pageable pageable) {
        return service.findAll(example, pageable);
    }

    @GetMapping("{id}")
    public TodoService.TodoDTO getById(@PathVariable UUID id) {
        return service.getByUUID(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TodoService.TodoDTO create(@RequestBody TodoService.TodoCreateDTO dto) {
        return service.create(dto);
    }

    @PutMapping("{id}")
    public TodoService.TodoDTO update(@PathVariable UUID id, @RequestBody TodoService.TodoUpdateDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
