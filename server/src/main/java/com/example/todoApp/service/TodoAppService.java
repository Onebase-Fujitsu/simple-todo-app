package com.example.todoApp.service;

import com.example.todoApp.controller.TodoAppControllerInterface;
import com.example.todoApp.model.NewTask;
import com.example.todoApp.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class TodoAppService implements TodoAppControllerInterface {
    @Autowired
    TodoAppServiceInterface todoAppRepository;

    @Autowired
    ClockServiceInterface clockService;

    @Override
    public List<Task> getAllTasks() {
        return todoAppRepository.getAllTasks();
    }

    @Override
    public void createNewTask(NewTask newTask) {
        todoAppRepository.createNewTask(newTask, clockService.now());
    }

    @Override
    public void finishTask(int taskId) {
        todoAppRepository.finishTask(taskId, clockService.now());
    }

    @Override
    public void revertTask(int taskId) {
        todoAppRepository.revertTask(taskId, clockService.now());
    }

    @Override
    public void deleteTask(int taskId) {
        todoAppRepository.deleteTask(taskId, clockService.now());
    }
}
