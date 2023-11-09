package com.fabricefo.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fabricefo.demo.exception.ResourceNotFoundException;
import com.fabricefo.demo.model.Tag;
import com.fabricefo.demo.model.Todo;
import com.fabricefo.demo.repository.TagRepository;
import com.fabricefo.demo.repository.TodoRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TagController {

  @Autowired
  private TodoRepository todoRepository;

  @Autowired
  private TagRepository tagRepository;

  @GetMapping("/tags")
  public ResponseEntity<List<Tag>> getAllTags() {
    List<Tag> tags = new ArrayList<Tag>();

    tagRepository.findAll().forEach(tags::add);

    if (tags.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(tags, HttpStatus.OK);
  }
  
  @GetMapping("/todos/{todoId}/tags")
  public ResponseEntity<List<Tag>> getAllTagsByTodoId(@PathVariable(value = "todoId") Long todoId) {
    if (!todoRepository.existsById(todoId)) {
      throw new ResourceNotFoundException("Not found Todo with id = " + todoId);
    }

    List<Tag> tags = tagRepository.findTagsByTodosId(todoId);
    return new ResponseEntity<>(tags, HttpStatus.OK);
  }

  @GetMapping("/tags/{id}")
  public ResponseEntity<Tag> getTagsById(@PathVariable(value = "id") Long id) {
    Tag tag = tagRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + id));

    return new ResponseEntity<>(tag, HttpStatus.OK);
  }
  
  @GetMapping("/tags/{tagId}/todos")
  public ResponseEntity<List<Todo>> getAllTodosByTagId(@PathVariable(value = "tagId") Long tagId) {
    if (!tagRepository.existsById(tagId)) {
      throw new ResourceNotFoundException("Not found Tag  with id = " + tagId);
    }

    List<Todo> todos = todoRepository.findTodosByTagsId(tagId);
    return new ResponseEntity<>(todos, HttpStatus.OK);
  }

  @PostMapping("/todos/{todoId}/tags")
  public ResponseEntity<Tag> addTag(@PathVariable(value = "todoId") Long todoId, @RequestBody Tag tagRequest) {
    Tag tag = todoRepository.findById(todoId).map(todo -> {
      long tagId = tagRequest.getId();
      
      // tag is existed
      if (tagId != 0L) {
        Tag _tag = tagRepository.findById(tagId)
            .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + tagId));
        todo.addTag(_tag);
        todoRepository.save(todo);
        return _tag;
      }
      
      // add and create new Tag
      todo.addTag(tagRequest);
      return tagRepository.save(tagRequest);
    }).orElseThrow(() -> new ResourceNotFoundException("Not found Todo with id = " + todoId));

    return new ResponseEntity<>(tag, HttpStatus.CREATED);
  }

  @PutMapping("/tags/{id}")
  public ResponseEntity<Tag> updateTag(@PathVariable("id") long id, @RequestBody Tag tagRequest) {
    Tag tag = tagRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("TagId " + id + "not found"));

    tag.setName(tagRequest.getName());

    return new ResponseEntity<>(tagRepository.save(tag), HttpStatus.OK);
  }
 
  @DeleteMapping("/todos/{todoId}/tags/{tagId}")
  public ResponseEntity<HttpStatus> deleteTagFromTodo(@PathVariable(value = "todoId") Long todoId, @PathVariable(value = "tagId") Long tagId) {
    Todo todo = todoRepository.findById(todoId)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Todo with id = " + todoId));
    
    todo.removeTag(tagId);
    todoRepository.save(todo);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @DeleteMapping("/tags/{id}")
  public ResponseEntity<HttpStatus> deleteTag(@PathVariable("id") long id) {
    tagRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
