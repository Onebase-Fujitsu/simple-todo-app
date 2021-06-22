package com.example.todoApp.service;

import com.example.todoApp.model.NewTask;
import com.example.todoApp.model.Task;
import com.example.todoApp.repository.TodoAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TodoAppService {
    List<Task> getAllTasks();
    void createNewTask(NewTask newTask);
    void finishTask(int taskId);
    void revertTask(int taskId);
    void deleteTask(int taskId);
}

@Service
class TodoAppServiceImpl implements TodoAppService {
    @Autowired
    TodoAppRepository todoAppRepository;

    @Override
    public List<Task> getAllTasks() {
        return todoAppRepository.getAllTasks();
    }

    @Override
    public void createNewTask(NewTask newTask) {
        todoAppRepository.createNewTask(newTask);
    }

    @Override
    public void finishTask(int taskId) {
        todoAppRepository.finishTask(taskId);
    }

    @Override
    public void revertTask(int taskId) {
        todoAppRepository.revertTask(taskId);
    }

    @Override
    public void deleteTask(int taskId) {
        todoAppRepository.deleteTask(taskId);
    }
}
