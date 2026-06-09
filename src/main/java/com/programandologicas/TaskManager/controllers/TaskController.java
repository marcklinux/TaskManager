package com.programandologicas.TaskManager.controllers;

import com.programandologicas.TaskManager.dto.PlanTasksResponse;
import com.programandologicas.TaskManager.dto.RequestTask;
import com.programandologicas.TaskManager.dto.ResponseTask;
import com.programandologicas.TaskManager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/plan/{planId}")
    public ResponseEntity<PlanTasksResponse> obtenerTareasPorPlan(@PathVariable int planId) {
        PlanTasksResponse response = taskService.obtenerTareasPorPlan(planId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseTask> crearTarea(@Valid @RequestBody RequestTask request) {
        ResponseTask response = taskService.crearTarea(request);
        return ResponseEntity.ok(response);
    }
}

