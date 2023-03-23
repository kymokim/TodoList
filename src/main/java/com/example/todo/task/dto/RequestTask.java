package com.example.todo.task.dto;

import com.example.todo.task.entity.Task;
import lombok.Builder;
import lombok.Data;

public class RequestTask {
    @Data
    @Builder
    public static class CreateTaskDto {
        private String title;
        private String content;

        public static Task of(CreateTaskDto createTaskDto){
            return Task.builder()
                    .title(createTaskDto.getTitle())
                    .content(createTaskDto.getContent())
                    .build();
        }
    }

    @Data
    @Builder
    public static class UpdateTaskDto {
        private Long id;
        private String title;
        private String content;
    }

    @Data
    @Builder
    public static class CompleteTaskDto {
        private Long id;
        private boolean isCompleted;
    }
}
