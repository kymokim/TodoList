package com.example.todo.task.service;

import com.example.todo.common.exception.ErrorCode;
import com.example.todo.common.exception.error.NotFoundTaskException;
import com.example.todo.task.dto.RequestTask;
import com.example.todo.task.dto.ResponseTask;
import com.example.todo.task.entity.Task;
import com.example.todo.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService { // 오류 던지긴 하는데 안 날라옴

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTask(RequestTask.CreateTaskDto createTaskDto){

        Task task = RequestTask.CreateTaskDto.toEntity(createTaskDto);
        taskRepository.save(task);
    }

    public List<ResponseTask.GetAllTaskDto> getAllTask(){

        List<Task> tasks = taskRepository.findAll();
        List<ResponseTask.GetAllTaskDto> list = new ArrayList<>();
        tasks.stream().forEach(task -> list.add(ResponseTask.GetAllTaskDto.toDto(task)));
        return list;
    }

    public ResponseTask.GetTaskDto getTask(Long id){

        Task task = taskRepository.findById(id).get();
        if(task == null) { throw new NotFoundTaskException(); }
        return ResponseTask.GetTaskDto.toDto(task);
    }

    public void updateTask(RequestTask.UpdateTaskDto updateTaskDto) {

        Task originalTask = taskRepository.findById(updateTaskDto.getId()).get();
        if(originalTask == null) { throw new NotFoundTaskException(); }
        Task updatedTask = RequestTask.UpdateTaskDto.toEntity(originalTask, updateTaskDto);
        taskRepository.save(updatedTask);
    }

    public void completeTask(RequestTask.CompleteTaskDto completeTaskDto) {

        Task originalTask = taskRepository.findById(completeTaskDto.getId()).get();
        if(originalTask == null){ throw new NotFoundTaskException(); }
        Task completedTask = RequestTask.CompleteTaskDto.toEntity(originalTask, completeTaskDto);
        taskRepository.save(completedTask);
    }

    public void deleteTask(Long id) {

        Task task = taskRepository.findById(id).get();
        if(task == null) { throw new NotFoundTaskException(); }
        taskRepository.delete(task);
    }
}
