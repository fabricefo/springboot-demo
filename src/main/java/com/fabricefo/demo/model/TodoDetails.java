package com.fabricefo.demo.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "todo_details")
public class TodoDetails {
  @Id
  private Long id;

  @Column
  private Date createdOn;

  @Column
  private String createdBy;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(name = "todo_id")
  private Todo todo;

  public TodoDetails() {
  }

  public TodoDetails(String createdBy) {
    this.createdOn = new Date();
    this.createdBy = createdBy;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Todo getTodo() {
    return todo;
  }

  public void setTodo(Todo todo) {
    this.todo = todo;
  }

}
