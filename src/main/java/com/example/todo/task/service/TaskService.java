package com.example.todo.task.service;

import com.example.todo.common.exception.ErrorCode;
import com.example.todo.common.exception.error.NotFoundTaskException;
import com.example.todo.task.dto.RequestTask;
import com.example.todo.task.dto.ResponseTask;
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

        taskRepository.save(RequestTask.CreateTaskDto.toEntity(createTaskDto));
    }

    public List<ResponseTask.GetAllTaskDto> getAllTask(){

        List<ResponseTask.GetAllTaskDto> list = new ArrayList<>();
        taskRepository.findAll().stream().forEach(task -> list.add(ResponseTask.GetAllTaskDto.toDto(task)));
        return list;
    }

    public ResponseTask.GetTaskDto getTask(Long id){

        if(taskRepository.findById(id).isEmpty()) { throw new NotFoundTaskException(); }
        return ResponseTask.GetTaskDto.toDto(taskRepository.findById(id).get());
    }

    public void updateTask(RequestTask.UpdateTaskDto updateTaskDto) {

        if(taskRepository.findById(updateTaskDto.getId()).isEmpty()) { throw new NotFoundTaskException(); }
        taskRepository.save(RequestTask.UpdateTaskDto.toEntity(taskRepository.findById(updateTaskDto.getId()).get(), updateTaskDto));
    }

    public void completeTask(RequestTask.CompleteTaskDto completeTaskDto) {

        if(taskRepository.findById(completeTaskDto.getId()).isEmpty()){ throw new NotFoundTaskException(); }
        taskRepository.save(RequestTask.CompleteTaskDto.toEntity(taskRepository.findById(completeTaskDto.getId()).get(), completeTaskDto));
    }

    public void deleteTask(Long id) {

        if(taskRepository.findById(id).isEmpty()) { throw new NotFoundTaskException(); }
        taskRepository.delete(taskRepository.findById(id).get());
    }

}
