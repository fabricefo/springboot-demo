package com.fabricefo.demo.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tags")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name")
  private String name;

  @ManyToMany(fetch = FetchType.LAZY,
      cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE
      },
      mappedBy = "tags")
  @JsonIgnore
  private Set<Todo> todos = new HashSet<>();

  public Tag() {

  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Todo> getTodos() {
    return todos;
  }

  public void setTutorials(Set<Todo> todos) {
    this.todos = todos;
  }  
  
}