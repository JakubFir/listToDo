package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {
    @GetMapping
    public List<TaskDto> getTasks() {
        return new ArrayList<>();
    }

    @GetMapping(path = "{taskId}")
    public TaskDto getTask(@PathVariable("taskId") Long taskId) {
        return new TaskDto(1L, "test title", "test_content");
    }

    @DeleteMapping(path = "{taskId}")
    public void deleteTask(@PathVariable("taskId") Long taskId) {

    }

    @PutMapping()
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto(1L, "Edited test title", "Test content");
    }
    @PostMapping
    public void createTask(@RequestBody TaskDto taskDto) {

    }
}