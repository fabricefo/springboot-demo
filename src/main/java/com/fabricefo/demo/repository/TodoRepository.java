package com.fabricefo.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.fabricefo.demo.model.Todo;;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByTitleContaining(String title);
}
