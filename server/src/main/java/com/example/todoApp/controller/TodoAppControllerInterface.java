package com.example.todoApp.controller;

import com.example.todoApp.model.NewTask;
import com.example.todoApp.model.Task;

import java.util.List;

public interface TodoAppControllerInterface {
    List<Task> getAllTasks();
    void createNewTask(NewTask newTask);
    void finishTask(int taskId);
    void revertTask(int taskId);
    void deleteTask(int taskId);
}
