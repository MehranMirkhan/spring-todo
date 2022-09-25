package com.fpt.todo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TodoServiceTests {
    @Autowired MockMvc      mvc;
    @Autowired TodoService  service;

    @BeforeEach
    void setUp() {
        Assertions.assertEquals(0, service.findAll().getSize());
        service.create(TodoService.TodoCreateDTO.builder().text("Task 1").build());
        service.create(TodoService.TodoCreateDTO.builder().text("Task 2").build());
        service.create(TodoService.TodoCreateDTO.builder().text("Task 3").build());
        Assertions.assertEquals(3, service.findAll().getSize());
    }

    @AfterEach
    void tearDown() {
        service.deleteAll();
    }

    @Test
    void testFindAll() {
        var data = service.findAll();
        Assertions.assertEquals(3, data.getTotalElements());
    }

    @Test
    void testSearch() {
        var search = TodoService.TodoSearchDTO.builder().text("2").build();
        var data   = service.findAll(search, Pageable.unpaged());
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
    void testLifeCycle() {
        // Create
        var  dto  = TodoService.TodoCreateDTO.builder().text("Example Task").build();
        var  todo = service.create(dto);
        UUID uuid = todo.uuid();
        Assertions.assertEquals(uuid, service.getByUUID(uuid).uuid());
        var search = TodoService.TodoSearchDTO.builder().text("xam").build();
        Assertions.assertEquals(uuid, service.findAll(search, Pageable.unpaged())
                                             .stream().findFirst().orElseThrow().uuid());
        Assertions.assertEquals(4, service.findAll().getTotalElements());

        // Update
        service.update(uuid, TodoService.TodoUpdateDTO.builder().text("New Task").done(todo.done()).build());
        TodoService.TodoDTO newTodoFromDB = service.getByUUID(uuid);
        Assertions.assertEquals("New Task", newTodoFromDB.text());
        Assertions.assertEquals(todo.done(), newTodoFromDB.done());

        // Delete
        service.delete(uuid);
        Assertions.assertThrows(RuntimeException.class, () -> service.getByUUID(uuid));
        Assertions.assertEquals(3, service.findAll().getTotalElements());
        Assertions.assertEquals(0, service.findAll(search, Pageable.unpaged()).getTotalElements());
    }

    @Test
    void testNotFoundError() throws Exception {
        UUID uuid = UUID.randomUUID();
        mvc.perform(get("/api/todo/" + uuid)
                            .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isNotFound())
           .andExpect(jsonPath("$.id", notNullValue()))
           .andExpect(jsonPath("$.payload.uuid", is(uuid.toString())));
    }
}
