package com.example.todo.task.dto;

import lombok.Builder;
import lombok.Data;

public class RequestTask {
    @Data
    @Builder
    public static class create {
        private String title;
        private String content;
        private boolean completed;
    }

    @Data
    @Builder
    public static class update {
        private String title;
        private String content;
        private boolean completed;
    }
}
