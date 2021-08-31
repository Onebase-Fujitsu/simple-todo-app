package com.example.todoApp.controller;
import com.example.todoApp.model.NewTask;
import com.example.todoApp.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TodoAppControllerUnitTest {
    @Mock
    private TodoAppControllerInterface appService;

    @InjectMocks
    private TodoAppController appController;

    @Captor
    private ArgumentCaptor<NewTask> argCaptor;

    @Test
    public void タスクが取得できなかったら空のタスクリストを返す() {
        when(appService.getAllTasks()).thenReturn(Collections.emptyList());
        List<Task> tasks = appController.getAllTasks();
        verify(appService, times(1)).getAllTasks();
        assertThat(tasks.size()).isEqualTo(0);
    }

    @Test
    public void 複数のタスクをリストで返す() {
        when(appService.getAllTasks()).thenReturn(List.of(
                new Task(1, "hoge", "fuga", false, OffsetDateTime.parse("2021-08-19T15:00:00+09:00"), OffsetDateTime.parse("2021-08-19T16:00:00+09:00")),
                new Task(2, "foo", "bar", true, OffsetDateTime.parse("2021-08-20T15:00:00+09:00"), OffsetDateTime.parse("2021-08-20T16:00:00+09:00"))));
        List<Task> tasks = appController.getAllTasks();
        verify(appService, times(1)).getAllTasks();
        assertThat(tasks.size()).isEqualTo(2);
        assertThat(tasks.get(0).id).isEqualTo(1);
        assertThat(tasks.get(0).title).isEqualTo("hoge");
        assertThat(tasks.get(0).description).isEqualTo("fuga");
        assertThat(tasks.get(0).isDone).isEqualTo(false);
        assertThat(tasks.get(0).createdAt).isEqualTo(OffsetDateTime.parse("2021-08-19T15:00:00+09:00"));
        assertThat(tasks.get(0).updatedAt).isEqualTo(OffsetDateTime.parse("2021-08-19T16:00:00+09:00"));

        assertThat(tasks.get(1).id).isEqualTo(2);
        assertThat(tasks.get(1).title).isEqualTo("foo");
        assertThat(tasks.get(1).description).isEqualTo("bar");
        assertThat(tasks.get(1).isDone).isEqualTo(true);
        assertThat(tasks.get(1).createdAt).isEqualTo(OffsetDateTime.parse("2021-08-20T15:00:00+09:00"));
        assertThat(tasks.get(1).updatedAt).isEqualTo(OffsetDateTime.parse("2021-08-20T16:00:00+09:00"));
    }

    @Test
    public void DBアクセスエラーを検知したときResponseStatusExceptionを投げる() {
        when(appService.getAllTasks()).thenThrow(new DataAccessException("..."){ });
        assertThrows(ResponseStatusException.class, () -> {
            appController.getAllTasks();
        });
    }

    @Test
    public void 新規のタスク作成処理を呼ぶ際に引数を正しくセットしている() {
        NewTask task = new NewTask("Task Title", "Task Description");
        appController.createTask(task);
        verify(appService).createNewTask(argCaptor.capture());
        assertThat(argCaptor.getValue().title).isEqualTo("Task Title");
        assertThat(argCaptor.getValue().description).isEqualTo("Task Description");
    }
}