package com.programandologicas.TaskManager.controllers;

import com.programandologicas.TaskManager.dto.*;
import com.programandologicas.TaskManager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDate;
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

    @GetMapping("/rango-fecha")
    public ResponseEntity<List<ResponseTask>> obtenerTareasPorRangoFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<ResponseTask> response = taskService.obtenerTareasPorRangoFecha(fechaInicio, fechaFin);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<ResponseTask> crearTarea(@Valid @RequestBody RequestTask request) {
        ResponseTask response = taskService.crearTarea(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTask> actualizarTarea(@PathVariable int id, @Valid @RequestBody RequestTask request) {
        ResponseTask response = taskService.actualizarTarea(id, request);
        return ResponseEntity.ok(response);
    }

}
