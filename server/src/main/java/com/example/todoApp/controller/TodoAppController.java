package com.example.todoApp.controller;

import com.example.todoApp.model.NewTask;
import com.example.todoApp.model.Task;
import com.example.todoApp.service.TodoAppService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class TodoAppController {
    @Autowired
    TodoAppService todoAppService;

    @GetMapping("/tasks")
    @ApiOperation(
            value = "get all tasks",
            produces = "application/json",
            consumes = "application/json"
    )
    public List<Task> getAllTasks() {
        try {
            return todoAppService.getAllTasks();
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(
            value = "create new task",
            produces = "application/json",
            consumes = "application/json"
    )
    public void createTask(@RequestBody NewTask newTask) {
        try {
            todoAppService.createNewTask(newTask);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tasks/{id}/finish")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "finish task",
            produces = "application/json",
            consumes = "application/json"
    )
    public void finishTask(@PathVariable("id") int taskId) {
        try {
            todoAppService.finishTask(taskId);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tasks/{id}/revert")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "revert task",
            produces = "application/json",
            consumes = "application/json"
    )
    public void revertTask(@PathVariable("id") int taskId) {
        try {
            todoAppService.revertTask(taskId);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(
            value = "delete task",
            produces = "application/json",
            consumes = "application/json"
    )
    public void deleteTask(@PathVariable("id") int taskId) {
        try {
            todoAppService.deleteTask(taskId);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
