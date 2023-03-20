package com.example.todo.auth.security;

public interface AuthToken<T> {
    boolean validate();
    T getClaims();
}
