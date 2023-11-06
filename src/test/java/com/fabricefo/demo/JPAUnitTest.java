package com.fabricefo.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.fabricefo.demo.repository.TodoRepository;
import com.fabricefo.demo.model.Todo;

@DataJpaTest
public class JPAUnitTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    TodoRepository repository;

    @Test
    public void should_find_no_todos_if_repository_is_empty() {
        Iterable<Todo> todos = repository.findAll();

        assertThat(todos).isEmpty();
    }

    @Test
    public void should_store_a_tutorial() {
        Todo todo = repository.save(new Todo("Todo title", "Todo desc"));
        assertThat(todo).hasFieldOrPropertyWithValue("title", "Todo title");
        assertThat(todo).hasFieldOrPropertyWithValue("description", "Todo desc");        
    }

    @Test
    public void should_find_all_todos() {
      Todo todo1 = new Todo("Todo#1", "Desc#1");
      entityManager.persist(todo1);
  
      Todo todo2 = new Todo("Todo#2", "Desc#2");
      entityManager.persist(todo2);
  
      Todo todo3 = new Todo("Todo#3", "Desc#3");
      entityManager.persist(todo3);
  
      Iterable<Todo> todos = repository.findAll();
  
      assertThat(todos).hasSize(3).contains(todo1, todo2, todo3);
    }
  
    @Test
    public void should_find_todo_by_id() {
      Todo todo1 = new Todo("Todo#1", "Desc#1");
      entityManager.persist(todo1);
  
      Todo todo2 = new Todo("Todo#2", "Desc#2");
      entityManager.persist(todo2);
  
      Todo foundTodo = repository.findById(todo2.getId()).get();
  
      assertThat(foundTodo).isEqualTo(todo2);
    }
  
    @Test
    public void should_find_todos_by_title_containing_string() {
      Todo todo1 = new Todo("Spring Boot Todo#1", "Desc#1");
      entityManager.persist(todo1);
  
      Todo todo2 = new Todo("Java Todo#2", "Desc#2");
      entityManager.persist(todo2);
  
      Todo todo3 = new Todo("Spring Data JPA Todo#3", "Desc#3");
      entityManager.persist(todo3);
  
      Iterable<Todo> todos = repository.findByTitleContaining("ring");
  
      assertThat(todos).hasSize(2).contains(todo1, todo3);
    }
  
    @Test
    public void should_update_todo_by_id() {
      Todo todo1 = new Todo("Todo#1", "Desc#1");
      entityManager.persist(todo1);
  
      Todo todo2 = new Todo("Todo#2", "Desc#2");
      entityManager.persist(todo2);
  
      Todo updatedTodo = new Todo("updated Todo#2", "updated Desc#2");
  
      Todo todo = repository.findById(todo2.getId()).get();
      todo.setTitle(updatedTodo.getTitle());
      todo.setDescription(updatedTodo.getDescription());
      repository.save(todo);
  
      Todo checkTodo = repository.findById(todo2.getId()).get();
      
      assertThat(checkTodo.getId()).isEqualTo(todo2.getId());
      assertThat(checkTodo.getTitle()).isEqualTo(updatedTodo.getTitle());
      assertThat(checkTodo.getDescription()).isEqualTo(updatedTodo.getDescription());
    }
  
    @Test
    public void should_delete_todo_by_id() {
      Todo todo1 = new Todo("Tut#1", "Desc#1");
      entityManager.persist(todo1);
  
      Todo todo2 = new Todo("Tut#2", "Desc#2");
      entityManager.persist(todo2);
  
      Todo todo3 = new Todo("Tut#3", "Desc#3");
      entityManager.persist(todo3);
  
      repository.deleteById(todo2.getId());
  
      Iterable<Todo> todos = repository.findAll();
  
      assertThat(todos).hasSize(2).contains(todo1, todo3);
    }
  
    @Test
    public void should_delete_all_todos() {
      entityManager.persist(new Todo("Tut#1", "Desc#1"));
      entityManager.persist(new Todo("Tut#2", "Desc#2"));
  
      repository.deleteAll();
  
      assertThat(repository.findAll()).isEmpty();
    }
}
