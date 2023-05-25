package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

    private final TaskMapper taskMapper = new TaskMapper();

    @Test
    void mapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test", "test");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        assertEquals(taskDto.getId(), task.getId());
        assertEquals(taskDto.getTitle(), task.getTitle());
        assertEquals(taskDto.getContent(), task.getContent());
    }

    @Test
    void mapToTaskDto() {
        //Given
        Task task = new Task(1L, "test", "test");

        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);

        //Then
        assertEquals(task.getId(), taskDto.getId());
        assertEquals(task.getTitle(), taskDto.getTitle());
        assertEquals(task.getContent(), taskDto.getContent());

    }

    @Test
    void mapToTaskDtoList() {
        //Given
        List<Task> tasks = new ArrayList<>();
        Task task = new Task(1L, "test", "test");
        tasks.add(task);

        //When
        List<TaskDto> result = taskMapper.mapToTaskDtoList(tasks);

        //Then
        assertEquals(tasks.get(0).getId(), result.get(0).getId());
        assertEquals(tasks.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(tasks.get(0).getContent(), result.get(0).getContent());
    }
}