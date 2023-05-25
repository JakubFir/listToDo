package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService dbService;
    @MockBean
    private TaskMapper taskMapper;

    @Test
    void getTasks() throws Exception {
        //Given

        Task task = new Task(1L, "test", "Test");
        TaskDto taskDto = new TaskDto(1L, "test", "Test");
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        when(dbService.getAllTasks()).thenReturn(taskList);
        when(taskMapper.mapToTaskDtoList(taskList)).thenReturn(List.of(taskDto));

        //When && Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)));

    }

    @Test
    void getTaskById() throws Exception {
        //Given
        Task task = new Task(1L, "test", "Test");
        TaskDto taskDto = new TaskDto(1L, "test", "Test");

        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(dbService.getTaskById(1L)).thenReturn(task);

        //When && Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test")));

    }

    @Test
    void deleteTask() throws Exception {
        //Given
        Long id = 1L;

        //When && Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void updateTask() throws Exception {
        // Given
        TaskDto taskDto = new TaskDto(1L, "updateTest", "Test");
        Task task = new Task(1L, "updateTest", "Test");
        when(taskMapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When && Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("updateTest")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test")));
    }

    @Test
    void createTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "updateTest", "Test");
        Task task = new Task(1L, "updateTest", "Test");

        when(dbService.saveTask(task)).thenReturn(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When && Then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}