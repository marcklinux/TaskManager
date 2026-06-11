package com.programandologicas.TaskManager.controllers;

import com.programandologicas.TaskManager.dto.RequestPlan;
import com.programandologicas.TaskManager.dto.ResponsePlan;
import com.programandologicas.TaskManager.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanController {

    @Autowired
    private PlanService planService;

    @PostMapping
    public ResponseEntity<ResponsePlan> crearPlan(@Valid @RequestBody RequestPlan request) {
        ResponsePlan response = planService.crearPlan(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePlan> obtenerPlanPorId(@PathVariable int id) {
        ResponsePlan response = planService.obtenerPlanPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ResponsePlan>> obtenerTodosLosPlanes() {
        List<ResponsePlan> response = planService.obtenerTodosLosPlanes();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/proyecto/{proyectId}")
    public ResponseEntity<List<ResponsePlan>> obtenerPlanesPorProyecto(@PathVariable int proyectId) {
        List<ResponsePlan> response = planService.obtenerPlanesPorProyecto(proyectId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePlan> actualizarPlan(@PathVariable int id, @Valid @RequestBody RequestPlan request) {
        ResponsePlan response = planService.actualizarPlan(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlan(@PathVariable int id) {
        planService.eliminarPlan(id);
        return ResponseEntity.noContent().build();
    }
}
