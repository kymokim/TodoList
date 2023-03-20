package com.example.todo.task.dto;

import com.example.todo.task.entity.Task;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseTask {
    private Long id;
    private Long taskOrder;
    private String title;
    private String content;
    private boolean completed;

    public ResponseTask(Long id, Long taskOrder, String title, String content, boolean completed) {
        this.id = id;
        this.taskOrder = taskOrder;
        this.title = title;
        this.content = content;
        this.completed = completed;
    }


}

