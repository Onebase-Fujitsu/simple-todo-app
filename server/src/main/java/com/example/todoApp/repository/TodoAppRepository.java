package com.example.todoApp.repository;

import com.example.todoApp.model.NewTask;
import com.example.todoApp.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface TodoAppRepository {
    List<Task> getAllTasks();
    void createNewTask(NewTask newTask);
    void finishTask(int taskId);
    void revertTask(int taskId);
    void deleteTask(int taskId);
}

@Repository
class TodoAppRepositoryImpl implements TodoAppRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Task> getAllTasks() {
        List<Map<String, Object>> queryResult = jdbcTemplate.queryForList("select id, title, description, is_done from task where is_delete = false order by id");
        List<Task> tasks = new ArrayList<>();
        for (Map<String, Object> task : queryResult) {
            tasks.add(new Task(
                    (int) task.get("id"),
                    (String) task.get("title"),
                    (String) task.get("description"),
                    (boolean)task.get("is_done")));
        }
        return tasks;
    }

    @Override
    public void createNewTask(NewTask newTask) {
        jdbcTemplate.update("insert into task(title, description) values (?, ?)", newTask.title, newTask.description);
    }

    @Override
    public void finishTask(int taskId) {
        jdbcTemplate.update("update task set is_done = true where id = ?", taskId);
    }

    @Override
    public void revertTask(int taskId) {
        jdbcTemplate.update("update task set is_done = false where id = ?", taskId);
    }

    @Override
    public void deleteTask(int taskId) {
        jdbcTemplate.update("update task set is_delete = true where id = ?", taskId);
    }
}
