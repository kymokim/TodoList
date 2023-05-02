package com.example.todo.task.controller;

import com.example.todo.common.dto.ResponseMessage;
import com.example.todo.task.dto.RequestTask;
import com.example.todo.task.dto.ResponseTask;
import com.example.todo.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createTask(@RequestBody RequestTask.CreateTaskDto requestDto) {
        taskService.createTask(requestDto);
        ResponseMessage responseMessage = ResponseMessage.builder()
                .message("Task created successfully.")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseMessage> getAllTask() {
        List<ResponseTask.GetAllTaskDto> response = taskService.getAllTask();
        ResponseMessage responseMessage = ResponseMessage.builder()
                .message("Tasks retrieved successfully.")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseMessage> getTask(@PathVariable("id") Long id) {
        ResponseTask.GetTaskDto response = taskService.getTask(id);
        ResponseMessage responseMessage = ResponseMessage.builder()
                .message("Task retrieved successfully.")
                .data(response)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMessage> updateTask(@RequestBody RequestTask.UpdateTaskDto requestDto) {
        taskService.updateTask(requestDto);
        ResponseMessage responseMessage = ResponseMessage.builder()
                .message("Task updated successfully.")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @PutMapping("/complete")
    public ResponseEntity<ResponseMessage> completeTask(@RequestBody RequestTask.CompleteTaskDto requestDto) {
        taskService.completeTask(requestDto);
        ResponseMessage responseMessage = ResponseMessage.builder()
                .message("Task completed successfully.")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseMessage> deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        ResponseMessage responseMessage = ResponseMessage.builder()
                .message("Task deleted successfully.")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

    }
}
