package com.fabricefo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fabricefo.demo.exception.ResourceNotFoundException;
import com.fabricefo.demo.model.TodoDetails;
import com.fabricefo.demo.model.Todo;
import com.fabricefo.demo.repository.TodoDetailsRepository;
import com.fabricefo.demo.repository.TodoRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class TodoDetailsController {
  @Autowired
  private TodoDetailsRepository detailsRepository;

  @Autowired
  private TodoRepository todoRepository;

  @GetMapping({ "/details/{id}", "/todos/{id}/details" })
  public ResponseEntity<TodoDetails> getDetailsById(@PathVariable(value = "id") Long id) {
    TodoDetails details = detailsRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Todo Details with id = " + id));

    return new ResponseEntity<>(details, HttpStatus.OK);
  }

  @PostMapping("/todos/{todoId}/details")
  public ResponseEntity<TodoDetails> createDetails(@PathVariable(value = "todoId") Long todoId,
      @RequestBody TodoDetails detailsRequest) {
    Todo todo = todoRepository.findById(todoId)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Todo with id = " + todoId));

    detailsRequest.setCreatedOn(new java.util.Date());
    detailsRequest.setTodo(todo);
    TodoDetails details = detailsRepository.save(detailsRequest);

    return new ResponseEntity<>(details, HttpStatus.CREATED);
  }

  @PutMapping("/details/{id}")
  public ResponseEntity<TodoDetails> updateDetails(@PathVariable("id") long id,
      @RequestBody TodoDetails detailsRequest) {
    TodoDetails details = detailsRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Id " + id + " not found"));

    details.setCreatedBy(detailsRequest.getCreatedBy());

    return new ResponseEntity<>(detailsRepository.save(details), HttpStatus.OK);
  }

  @DeleteMapping("/details/{id}")
  public ResponseEntity<HttpStatus> deleteDetails(@PathVariable("id") long id) {
    detailsRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/todos/{todoId}/details")
  public ResponseEntity<TodoDetails> deleteDetailsOfTodo(@PathVariable(value = "todoId") Long todoId) {
    if (!todoRepository.existsById(todoId)) {
      throw new ResourceNotFoundException("Not found Todo with id = " + todoId);
    }

    detailsRepository.deleteByTodoId(todoId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
