package com.fabricefo.demo.model;

import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "todo_tags", joinColumns = { @JoinColumn(name = "todo_id") }, inverseJoinColumns = {
        @JoinColumn(name = "tag_id") })
    private Set<Tag> tags = new HashSet<>();

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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getTodos().add(this);
    }

    public void removeTag(long tagId) {
        Tag tag = this.tags.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
        if (tag != null) {
            this.tags.remove(tag);
            tag.getTodos().remove(this);
        }
    }

    @Override
    public String toString() {
        return "Todo [id=" + id + ", title=" + title + ", desc=" + description + "]";
    }


}
