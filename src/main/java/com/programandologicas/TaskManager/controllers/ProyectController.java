package com.programandologicas.TaskManager.controllers;

import com.programandologicas.TaskManager.dto.RequestProyect;
import com.programandologicas.TaskManager.dto.ResponseProyect;
import com.programandologicas.TaskManager.service.ProyectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
public class ProyectController {

    @Autowired
    private ProyectService proyectService;

    @PostMapping
    public ResponseEntity<ResponseProyect> crearProyecto(@Valid @RequestBody RequestProyect request) {
        ResponseProyect response = proyectService.crearProyecto(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProyect> obtenerProyectoPorId(@PathVariable int id) {
        ResponseProyect response = proyectService.obtenerProyectoPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ResponseProyect>> obtenerTodosLosProyectos() {
        List<ResponseProyect> response = proyectService.obtenerTodosLosProyectos();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseProyect> actualizarProyecto(@PathVariable int id, @Valid @RequestBody RequestProyect request) {
        ResponseProyect response = proyectService.actualizarProyecto(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable int id) {
        proyectService.eliminarProyecto(id);
        return ResponseEntity.noContent().build();
    }
}
