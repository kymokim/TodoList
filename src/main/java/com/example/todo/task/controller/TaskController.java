package com.example.todo.task.controller;

import com.example.todo.common.dto.ResponseMessage;
import com.example.todo.task.dto.ResponseTask;
import com.example.todo.task.entity.Task;
import com.example.todo.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("")
    public ResponseEntity<ResponseMessage> createTask(@RequestBody Task task) {
        ResponseTask createdTask = taskService.createTask(task);
        ResponseMessage responseMessage = ResponseMessage.builder()
                .message("Task created successfully")
                .data(createdTask)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    @GetMapping("")
    public ResponseEntity<ResponseMessage> getTasks() {
        List<ResponseTask> tasks = taskService.getTasks();
        ResponseMessage responseMessage = ResponseMessage.builder()
                .message("Tasks retrieved successfully")
                .data(tasks)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getTaskById(@PathVariable("id") Long id) {
        Optional<ResponseTask> task = taskService.getTaskById(id);
        ResponseMessage responseMessage = ResponseMessage.builder()
                .message("Task retrieved successfully")
                .data(task)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateTask(@PathVariable("id") Long id, @RequestBody Task task) {
        Optional<ResponseTask> updatedTask = taskService.updateTask(id, task);
        ResponseMessage responseMessage = ResponseMessage.builder()
                .message("Task updated successfully")
                .data(updatedTask)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteTask(@PathVariable("id") Long id) {
        boolean isDeleted = taskService.deleteTask(id);
        if (isDeleted) {
            ResponseMessage responseMessage = ResponseMessage.builder()
                    .message("Task deleted successfully")
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } else {
            ResponseMessage responseMessage = ResponseMessage.builder()
                    .message("Task not found")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
        }
    }
}
