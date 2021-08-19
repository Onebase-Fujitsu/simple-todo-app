package com.example.todoApp.controller;

import com.example.todoApp.model.Task;
import com.example.todoApp.service.ClockService;
import com.example.todoApp.repository.TodoAppRepository;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/clear_db.sql")
public class TodoAppControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ClockService clockService;

    @SpyBean
    private TodoAppRepository todoAppRepository;

    @BeforeEach
    public void setup() {
        doReturn(OffsetDateTime.parse("2021-08-19T15:00:00+09:00").toInstant()).when(clockService).now();
    }

    @Test
    public void なにもタスクを作成していない場合は0件が返す() {
        ResponseEntity<Task[]> response = restTemplate.exchange("/tasks", HttpMethod.GET, HttpEntity.EMPTY, Task[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).length).isEqualTo(0);
    }

    @Test
    public void タスクを新規に作成するとCREATEDを返す() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject taskJson = new JSONObject();
        taskJson.put("title", "foo");
        taskJson.put("description", "bar");

        ResponseEntity<String> response = restTemplate.exchange("/tasks", HttpMethod.POST, new HttpEntity<>(taskJson.toString(), headers), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Nested
    class 既存のタスクに対する操作 {
        @BeforeEach
        public void setup() {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject taskJson = new JSONObject();
            taskJson.put("title", "foo");
            taskJson.put("description", "bar");
            restTemplate.exchange("/tasks", HttpMethod.POST, new HttpEntity<>(taskJson.toString(), headers), Object.class);
        }

        @Test
        public void タスクがあるとその情報を取得できる() {
            ResponseEntity<Task[]> response = restTemplate.exchange("/tasks", HttpMethod.GET, HttpEntity.EMPTY, Task[].class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(Objects.requireNonNull(response.getBody()).length).isEqualTo(1);

            assertThat(response.getBody()[0].id).isEqualTo(1);
            assertThat(response.getBody()[0].title).isEqualTo("foo");
            assertThat(response.getBody()[0].description).isEqualTo("bar");
            assertThat(response.getBody()[0].isDone).isEqualTo(false);
            assertThat(response.getBody()[0].createdAt).isEqualTo("2021-08-19T15:00:00+09:00");
            assertThat(response.getBody()[0].updatedAt).isEqualTo("2021-08-19T15:00:00+09:00");
        }

        @Test
        public void タスクを完了できる() {
            doReturn(clockService.now().plus(30, ChronoUnit.MINUTES)).when(clockService).now();
            ResponseEntity<Object> operationResponse = restTemplate.exchange("/tasks/1/finish", HttpMethod.PUT, HttpEntity.EMPTY, Object.class);
            assertThat(operationResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

            ResponseEntity<Task[]> response = restTemplate.exchange("/tasks", HttpMethod.GET, HttpEntity.EMPTY, Task[].class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(Objects.requireNonNull(response.getBody()).length).isEqualTo(1);

            assertThat(response.getBody()[0].id).isEqualTo(1);
            assertThat(response.getBody()[0].title).isEqualTo("foo");
            assertThat(response.getBody()[0].description).isEqualTo("bar");
            assertThat(response.getBody()[0].isDone).isEqualTo(true);
            assertThat(response.getBody()[0].createdAt).isEqualTo("2021-08-19T15:00:00+09:00");
            assertThat(response.getBody()[0].updatedAt).isEqualTo("2021-08-19T15:30:00+09:00");
        }
    }

    @Nested
    class DBアクセスエラー {
        @Test
        public void 取得メソッドでDBアクセスエラーが発生するとInternalServerErrorを返す() {
            doThrow(new DataAccessException("...") {}).when(todoAppRepository).getAllTasks();
            ResponseEntity<String> response = restTemplate.exchange("/tasks", HttpMethod.GET, HttpEntity.EMPTY, String.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @Test
        public void 作成メソッドでDBアクセスエラーが発生するとInternalServerErrorを返す() {
            doThrow(new DataAccessException("..."){}).when(todoAppRepository).createNewTask(any(), any());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject taskJson = new JSONObject();
            taskJson.put("title", "foo");
            taskJson.put("description", "bar");
            ResponseEntity<String> response = restTemplate.exchange("/tasks", HttpMethod.POST, new HttpEntity<>(taskJson.toString(), headers), String.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}