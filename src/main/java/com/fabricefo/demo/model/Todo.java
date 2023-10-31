package com.fabricefo.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name="todos")
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

    public Todo() {}

    public Todo(String title, String description) {
        this.title=title;
        this.description=description;
    }

    public Todo(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    } 

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Todo [id=" + id + ", title=" + title + ", desc=" + description + "]";
    }


}
