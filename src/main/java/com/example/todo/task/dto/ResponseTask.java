package com.example.todo.task.dto;

import com.example.todo.task.entity.Task;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

public class ResponseTask {

    @Builder
    @Getter
    public static class GetTaskDto{
//        private Long id;
        private LocalDateTime creationDate;
        private String title;
        private String content;
        private boolean isCompleted;

        public static GetTaskDto of(Task task){
            return GetTaskDto.builder()
//                    .id(task.getId())
                    .creationDate(task.getCreationDate())
                    .title(task.getTitle())
                    .content(task.getContent())
                    .isCompleted(task.isCompleted())
                    .build();
        }
    }

    @Builder
    @Getter
    public static class GetAllTaskDto{
        private Long id;
        private LocalDateTime creationDate;
        private String title;
        private String content;
        private boolean isCompleted;

        public static GetAllTaskDto of(Task task){
            return GetAllTaskDto.builder()
                    .id(task.getId())
                    .creationDate(task.getCreationDate())
                    .title(task.getTitle())
                    .content(task.getContent())
                    .isCompleted(task.isCompleted())
                    .build();
        }
    }
}

