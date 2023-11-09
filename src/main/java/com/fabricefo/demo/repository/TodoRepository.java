package com.fabricefo.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fabricefo.demo.model.Todo;;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByTitleContaining(String title);
    List<Todo> findTodosByTagsId(Long tagId);

}
