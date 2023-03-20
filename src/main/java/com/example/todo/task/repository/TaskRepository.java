package com.example.todo.task.repository;

import com.example.todo.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTaskOrderGreaterThanEqual(Long taskOrder);
    @Query("SELECT MAX(t.taskOrder) FROM Task t")
    Optional<Long> findMaxTaskOrder();
}