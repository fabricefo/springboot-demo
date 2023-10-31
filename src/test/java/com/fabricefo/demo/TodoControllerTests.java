package com.fabricefo.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fabricefo.demo.controller.TodoController;
import com.fabricefo.demo.model.Todo;
import com.fabricefo.demo.repository.TodoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TodoController.class)
public class TodoControllerTests {
    
    @MockBean
    private TodoRepository todoRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void shouldCreateTodo() throws Exception {
        Todo todo = new Todo(1, "Spring Boot @WebMvcTest", "Description");

        mockMvc.perform(post("/api/todos").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(todo)))
            .andExpect(status().isCreated())
            .andDo(print());

    }

    @Test
    void shouldReturnTodo() throws Exception {
      long id = 1L;
      Todo todo = new Todo(id, "Spring Boot @WebMvcTest", "Description");
  
      when(todoRepository.findById(id)).thenReturn(Optional.of(todo));
      mockMvc.perform(get("/api/todos/{id}", id)).andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(id))
          .andExpect(jsonPath("$.title").value(todo.getTitle()))
          .andExpect(jsonPath("$.description").value(todo.getDescription()))
          .andDo(print());
    }
  
    @Test
    void shouldReturnNotFoundTodo() throws Exception {
      long id = 1L;
  
      when(todoRepository.findById(id)).thenReturn(Optional.empty());
      mockMvc.perform(get("/api/todos/{id}", id))
           .andExpect(status().isNotFound())
           .andDo(print());
    }
  
    @Test
    void shouldReturnListOfTodos() throws Exception {
      List<Todo> todos = new ArrayList<>(
          Arrays.asList(new Todo(1, "Spring Boot @WebMvcTest 1", "Description 1"),
              new Todo(2, "Spring Boot @WebMvcTest 2", "Description 2"),
              new Todo(3, "Spring Boot @WebMvcTest 3", "Description 3")));
  
      when(todoRepository.findAll()).thenReturn(todos);
      mockMvc.perform(get("/api/todos"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.size()").value(todos.size()))
          .andDo(print());
    }
  
    @Test
    void shouldReturnListOfTodosWithFilter() throws Exception {
      List<Todo> todos = new ArrayList<>(
          Arrays.asList(new Todo(1, "Spring Boot @WebMvcTest", "Description 1"),
              new Todo(3, "Spring Boot Web MVC", "Description 3")));
  
      String title = "Boot";
      MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
      paramsMap.add("title", title);
  
      when(todoRepository.findByTitleContaining(title)).thenReturn(todos);
      mockMvc.perform(get("/api/todos").params(paramsMap))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.size()").value(todos.size()))
          .andDo(print());
    }
    
    @Test
    void shouldReturnNoContentWhenFilter() throws Exception {
      String title = "Test";
      MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
      paramsMap.add("title", title);
      
      List<Todo> todos = Collections.emptyList();
  
      when(todoRepository.findByTitleContaining(title)).thenReturn(todos);
      mockMvc.perform(get("/api/todos").params(paramsMap))
          .andExpect(status().isNoContent())
          .andDo(print());
    }
  
    @Test
    void shouldUpdateTodo() throws Exception {
      long id = 1L;
  
      Todo todo = new Todo(id, "Spring Boot @WebMvcTest", "Description");
      Todo updatedtodo = new Todo(id, "Updated", "Updated");
  
      when(todoRepository.findById(id)).thenReturn(Optional.of(todo));
      when(todoRepository.save(any(Todo.class))).thenReturn(updatedtodo);
  
      mockMvc.perform(put("/api/todos/{id}", id).contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(updatedtodo)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.title").value(updatedtodo.getTitle()))
          .andExpect(jsonPath("$.description").value(updatedtodo.getDescription()))
          .andDo(print());
    }
    
    @Test
    void shouldReturnNotFoundUpdateTodo() throws Exception {
      long id = 1L;
  
      Todo updatedtodo = new Todo(id, "Updated", "Updated");
  
      when(todoRepository.findById(id)).thenReturn(Optional.empty());
      when(todoRepository.save(any(Todo.class))).thenReturn(updatedtodo);
  
      mockMvc.perform(put("/api/todos/{id}", id).contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(updatedtodo)))
          .andExpect(status().isNotFound())
          .andDo(print());
    }
    
    @Test
    void shouldDeleteTodo() throws Exception {
      long id = 1L;
  
      doNothing().when(todoRepository).deleteById(id);
      mockMvc.perform(delete("/api/todos/{id}", id))
           .andExpect(status().isNoContent())
           .andDo(print());
    }
    
    @Test
    void shouldDeleteAllTodos() throws Exception {
      doNothing().when(todoRepository).deleteAll();
      mockMvc.perform(delete("/api/todos"))
           .andExpect(status().isNoContent())
           .andDo(print());
    }
}
