package com.fabricefo.demo.repository;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fabricefo.demo.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findByTodoId(Long postId);
  
  @Transactional
  void deleteByTodoId(long todoId);
}
