package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TaskController {

    private final DbService service;
    private final TaskMapper taskMapper;

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks() {
        List<Task> tasks = service.getAllTasks();
        return ResponseEntity.ok(taskMapper.mapToTaskDtoList(tasks));
    }

    @GetMapping(path = "{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable("taskId") Long taskId) throws TaskNotFoundException {
        return new ResponseEntity<>(taskMapper.mapToTaskDto(service.getTaskById(taskId)), HttpStatus.OK);

    }

    @DeleteMapping(path = "{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") Long taskId) {
        service.deleteById(taskId);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        service.saveTask(task);
        return ResponseEntity.ok(taskMapper.mapToTaskDto(task));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        service.saveTask(task);
        return ResponseEntity.ok().build();
    }
}