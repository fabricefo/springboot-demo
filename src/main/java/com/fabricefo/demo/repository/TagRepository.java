package com.fabricefo.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabricefo.demo.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
  List<Tag> findTagsByTodosId(Long todoId);
}
