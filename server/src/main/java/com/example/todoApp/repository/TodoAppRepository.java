package com.example.todoApp.repository;

import com.example.todoApp.model.NewTask;
import com.example.todoApp.model.Task;
import com.example.todoApp.service.TodoAppServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
class TodoAppRepository implements TodoAppServiceInterface {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Task> getAllTasks() {
        List<Map<String, Object>> queryResult = jdbcTemplate.queryForList("select id, title, description, is_done, created_at, updated_at from task where is_delete = false order by id");
        List<Task> tasks = new ArrayList<>();
        for (Map<String, Object> task : queryResult) {
            tasks.add(new Task(
                    (int) task.get("id"),
                    (String) task.get("title"),
                    (String) task.get("description"),
                    (boolean) task.get("is_done"),
                    OffsetDateTime.ofInstant(((Timestamp)task.get("created_at")).toInstant(), ZoneId.of("Asia/Tokyo")),
                    OffsetDateTime.ofInstant(((Timestamp)task.get("updated_at")).toInstant(), ZoneId.of("Asia/Tokyo"))));
        }
        return tasks;
    }

    @Override
    public void createNewTask(NewTask newTask, Instant now) {
        jdbcTemplate.update("insert into task(title, description, created_at, updated_at) values (?, ?, ?, ?)", newTask.title, newTask.description, Timestamp.valueOf(now.atZone(ZoneId.of("Asia/Tokyo")).toLocalDateTime()), Timestamp.valueOf(now.atZone(ZoneId.of("Asia/Tokyo")).toLocalDateTime()));
    }

    @Override
    public void finishTask(int taskId, Instant now) {
        jdbcTemplate.update("update task set is_done = true, updated_at = ? where id = ?", Timestamp.valueOf(now.atZone(ZoneId.of("Asia/Tokyo")).toLocalDateTime()), taskId);
    }

    @Override
    public void revertTask(int taskId, Instant now) {
        jdbcTemplate.update("update task set is_done = false, updated_at = ? where id = ?", Timestamp.valueOf(now.atZone(ZoneId.of("Asia/Tokyo")).toLocalDateTime()), taskId);
    }

    @Override
    public void deleteTask(int taskId, Instant now) {
        jdbcTemplate.update("update task set is_delete = true, updated_at = ? where id = ?", Timestamp.valueOf(now.atZone(ZoneId.of("Asia/Tokyo")).toLocalDateTime()), taskId);
    }
}
