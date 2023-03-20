package com.example.todo.task.service;

import com.example.todo.task.dto.ResponseTask;
import com.example.todo.task.entity.Task;
import com.example.todo.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ResponseTask createTask(Task request) {
        Optional<Long> optionalMaxTaskOrder = taskRepository.findMaxTaskOrder();
        Long maxTaskOrder = optionalMaxTaskOrder.orElse(0L);
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setContent(request.getContent());
        task.setCompleted(false);
        task.setTaskOrder(maxTaskOrder + 1);
        Task savedTask = taskRepository.save(task);
        return new ResponseTask(savedTask.getId(), savedTask.getTaskOrder(), savedTask.getTitle(), savedTask.getContent(), savedTask.isCompleted());
    }

    public List<ResponseTask> getTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map((Task t) -> new ResponseTask(t.getId(), t.getTaskOrder(), t.getTitle(), t.getContent(), t.isCompleted()))
                .collect(Collectors.toList());
    }

    public Optional<ResponseTask> getTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(task -> new ResponseTask(task.getId(), task.getTaskOrder(), task.getTitle(), task.getContent(), task.isCompleted()));
    }

    public Optional<ResponseTask> updateTask(Long id, Task request) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(task -> {
            task.update(request);
            Task savedTask = taskRepository.save(task);
            return new ResponseTask(savedTask.getId(), savedTask.getTaskOrder(), savedTask.getTitle(), savedTask.getContent(), savedTask.isCompleted());
        });
    }

    public Optional<ResponseTask> completeTask(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(task -> {
            task.setCompleted(true);
            Task savedTask = taskRepository.save(task);
            return new ResponseTask(savedTask.getId(), savedTask.getTaskOrder(), savedTask.getTitle(), savedTask.getContent(), savedTask.isCompleted());
        });
    }

    public boolean deleteTask(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            Long taskOrder = task.getTaskOrder();
            taskRepository.deleteById(id);
            List<Task> tasksToUpdate = taskRepository.findByTaskOrderGreaterThanEqual(taskOrder);
            for (Task t : tasksToUpdate) {
                t.setTaskOrder(t.getTaskOrder() - 1);
                taskRepository.save(t);
            }
            return true;
        } else
            return false;
    }

}
