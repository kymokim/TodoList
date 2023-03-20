package com.example.todo.auth.dto;

import lombok.Builder;
import lombok.Data;

public class RequestAuth {
    @Builder
    @Data
    public static class register{
        private String email;
        private String password;
        private String username;
    }

    @Builder
    @Data
    public static class login{
        private String email;
        private String password;
    }

    @Builder
    @Data
    public static class update{
        private String password;
        private String username;
    }
}
