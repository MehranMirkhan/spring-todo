package com.fpt.todo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoServiceTests {
    @Autowired TodoService  service;

    @BeforeEach
    void setUp() {
        Assertions.assertEquals(0, service.findAll().getSize());
        TodoService.TodoCreateDTO todo1DTO = TodoService.TodoCreateDTO.builder().text("Task 1").build();
        TodoService.TodoCreateDTO todo2DTO = TodoService.TodoCreateDTO.builder().text("Task 2").build();
        TodoService.TodoCreateDTO todo3DTO = TodoService.TodoCreateDTO.builder().text("Task 3").build();
        service.create(todo1DTO);
        service.create(todo2DTO);
        service.create(todo3DTO);
        Assertions.assertEquals(3, service.findAll().getSize());
    }

    @AfterEach
    void tearDown() {
        service.deleteAll();
    }

    @Test
    void testFindAll() {
        Page<TodoService.TodoDTO> data = service.findAll();
        Assertions.assertEquals(3, data.getTotalElements());
    }

    @Test
    void testSearch() {
        var search = TodoService.TodoSearchDTO.builder().text("2").build();
        Page<TodoService.TodoDTO> data = service.findAll(search, Pageable.unpaged());
        Assertions.assertEquals(1, data.getTotalElements());
        Assertions.assertEquals("Task 2", data.getContent().get(0).text());
    }

    @Test
    void testPagination() {
        Page<TodoService.TodoDTO> data = service.findAll(Pageable.ofSize(2));
        Assertions.assertEquals(3, data.getTotalElements());
        Assertions.assertEquals(2, data.getContent().size());
        Assertions.assertEquals("Task 1", data.getContent().get(0).text());
        Assertions.assertEquals("Task 2", data.getContent().get(1).text());

        data = service.findAll(Pageable.ofSize(2).next());
        Assertions.assertEquals(3, data.getTotalElements());
        Assertions.assertEquals(1, data.getContent().size());
        Assertions.assertEquals("Task 3", data.getContent().get(0).text());
    }

    @Test
    void testGetById() {
        UUID uuid = service.findAll().get().findFirst().map(TodoService.TodoDTO::uuid).orElseThrow();
        TodoService.TodoDTO todo = service.getByUUID(uuid);
        Assertions.assertEquals("Task 1", todo.text());
    }

    @Test
    void testCreate() {
        var dto = TodoService.TodoCreateDTO.builder().text("Example Task").build();
        service.create(dto);
        Page<TodoService.TodoDTO> data = service.findAll(TodoService.TodoSearchDTO.builder().text("xam").build(),
                                                        Pageable.unpaged());
        Assertions.assertEquals(1, data.getTotalElements());
        Assertions.assertEquals("Example Task", data.getContent().get(0).text());
    }
}
