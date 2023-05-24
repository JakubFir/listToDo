package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DbServiceTest {

    @Mock
    private TaskRepository taskRepository;

    private DbService dbService;

    @BeforeEach
    void setUp() {
        dbService = new DbService(taskRepository);
    }

    @Test
    void getAllTasks() {
        //Given
        List<Task> list = List.of(new Task());
        when(taskRepository.findAll()).thenReturn(list);

        //When
        List<Task> result = dbService.getAllTasks();

        //Then
        assertEquals(result.size(), 1);
    }

    @Test
    void getTaskById() throws TaskNotFoundException {
        //Given
        Task task = new Task(1L,"test","test");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        //When
        Task result = dbService.getTaskById(1L);

        //Then
        assertEquals(result.getId(), task.getId());
        assertEquals(result.getTitle(), task.getTitle());
        assertEquals(result.getContent(), task.getContent());

    }

    @Test
    void saveTask() {
        //Given
        Task task = new Task(1L,"test","test");

        //When
        dbService.saveTask(task);

        ArgumentCaptor<Task> argumentCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(argumentCaptor.capture());
        Task capturedTask = argumentCaptor.getValue();

        //Then
        assertEquals(task.getContent(),capturedTask.getContent());
        assertEquals(task.getTitle(),capturedTask.getTitle());
        assertEquals(task.getId(),capturedTask.getId());

    }

    @Test
    void deleteById() {
        //Given
        Long id = 1L;

        //When
        dbService.deleteById(id);

        //Then
        verify(taskRepository).deleteById(id);

    }
}