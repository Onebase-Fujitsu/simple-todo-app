package com.example.todoApp.service;

import com.example.todoApp.model.NewTask;
import com.example.todoApp.model.Task;

import java.time.Instant;
import java.util.List;

public interface TodoAppServiceInterface {
    List<Task> getAllTasks();

    void createNewTask(NewTask newTask, Instant now);

    void finishTask(int taskId, Instant now);

    void revertTask(int taskId, Instant now);

    void deleteTask(int taskId, Instant now);
}
