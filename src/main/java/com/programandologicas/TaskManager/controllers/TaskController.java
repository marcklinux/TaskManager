package com.programandologicas.TaskManager.controllers;

import com.programandologicas.TaskManager.dto.PlanTasksResponse;
import com.programandologicas.TaskManager.dto.RequestTask;
import com.programandologicas.TaskManager.dto.ResponseTask;
import com.programandologicas.TaskManager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<ResponseTask>> obtenerTareas() {
        List<ResponseTask> response = taskService.obtenerTareas();
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<ResponseTask> crearTarea(@Valid @RequestBody RequestTask request) {
        ResponseTask response = taskService.crearTarea(request);
        return ResponseEntity.ok(response);
    }
}

