package com.example.todoApp.controller;

import com.example.todoApp.model.NewTask;
import com.example.todoApp.model.Task;
import com.example.todoApp.service.TodoAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoAppController {
    @Autowired
    TodoAppService todoAppService;

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return todoAppService.getAllTasks();
    }

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@RequestBody NewTask newTask) {
        todoAppService.createNewTask(newTask);
    }

    @PutMapping("/tasks/{id}/finish")
    @ResponseStatus(HttpStatus.OK)
    public void finishTask(@PathVariable("id") int taskId) {
        todoAppService.finishTask(taskId);
    }

    @PutMapping("/tasks/{id}/revert")
    @ResponseStatus(HttpStatus.OK)
    public void revertTask(@PathVariable("id") int taskId) {
        todoAppService.revertTask(taskId);
    }

    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable("id") int taskId) {
        todoAppService.deleteTask(taskId);
    }
}
