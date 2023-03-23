package com.example.todo.task.service;

import com.example.todo.common.exception.ErrorCode;
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
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTask(RequestTask.CreateTaskDto createTaskDto){
//        Task task = Task.builder()
//                .title(createTaskDto.getTitle())
//                .content(createTaskDto.getContent())
//                .build();
        taskRepository.save(RequestTask.CreateTaskDto.of(createTaskDto));
    }

    public List<ResponseTask.GetAllTaskDto> getAllTask(){
        List<ResponseTask.GetAllTaskDto> list = new ArrayList<>();
        List<Task> tasks = taskRepository.findAll();

        tasks.stream().forEach(task -> list.add(ResponseTask.GetAllTaskDto.of(task)));
        return list;
    }

    public ResponseTask.GetTaskDto getTask(Long id){
        Task task = (taskRepository.findById(id)).get();
        return ResponseTask.GetTaskDto.of(task);
    }

    public void updateTask(RequestTask.UpdateTaskDto updateTaskDto) {
        Task task = (taskRepository.findById(updateTaskDto.getId())).get();
        //나중에 optionalTask + isPresent 사용해서 예외처리 시도
        task.update(updateTaskDto.getTitle(), updateTaskDto.getContent());
        taskRepository.save(task);
    }

    public void completeTask(RequestTask.CompleteTaskDto completeTaskDto) {
        Task task = (taskRepository.findById(completeTaskDto.getId())).get();
        task.complete(completeTaskDto.isCompleted());
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = (taskRepository.findById(id)).get();
        taskRepository.delete(task);
    }

}
