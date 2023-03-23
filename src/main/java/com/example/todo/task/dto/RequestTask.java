package com.example.todo.task.dto;

import com.example.todo.task.entity.Task;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

public class RequestTask { // NotNull 적긴 했는데 오류가 안 날라감
    @Data
    @Builder
    public static class CreateTaskDto {
        @NotEmpty(message = "No title entered.")
        private String title;
        @NotEmpty(message = "No content entered.")
        private String content;

        public static Task toEntity(CreateTaskDto createTaskDto){
            return Task.builder()
                    .title(createTaskDto.getTitle())
                    .content(createTaskDto.getContent())
                    .build();
        }
    }

    @Data
    @Builder
    public static class UpdateTaskDto {
        @NotEmpty(message = "No task id entered.")
        private Long id;
        @NotEmpty(message = "No title entered.")
        private String title;
        @NotEmpty(message = "No content entered.")
        private String content;

        public static Task toEntity(Task task, UpdateTaskDto updateTaskDto){
            task.update(updateTaskDto.getTitle(), updateTaskDto.getContent());
            return task;
        }
    }

    @Data
    @Builder
    public static class CompleteTaskDto {
        @NotEmpty(message = "No task id entered.")
        private Long id;
        @NotEmpty(message = "No isCompleted entered.")
        private boolean isCompleted;

        public static Task toEntity(Task task, CompleteTaskDto completeTaskDto){
            task.complete(completeTaskDto.isCompleted());
            return task;
        }
    }
}
