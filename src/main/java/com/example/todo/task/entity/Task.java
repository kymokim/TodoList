package com.example.todo.task.entity;

import javax.persistence.*;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "bigint default 1")
    private Long taskOrder;

    private String title;

    private String content;

    private boolean completed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTaskOrder() {
        return taskOrder;
    }

    public void setTaskOrder(Long taskOrder) {
        this.taskOrder = taskOrder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void update(Task request) {
        this.setTitle(request.getTitle());
        this.setContent(request.getContent());
        this.setCompleted(request.isCompleted());
    }
}
