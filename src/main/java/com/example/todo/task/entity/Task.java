package com.example.todo.task.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "task")
@Entity
@Getter
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private LocalDateTime creationDate = LocalDateTime.now();

    private String title;

    private String content;

    private boolean isCompleted = false;

    @Builder
    public Task(String title, String content){ // 얜 왜 필요함??
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void complete(boolean isCompleted){
        this.isCompleted = isCompleted;
    }

}
