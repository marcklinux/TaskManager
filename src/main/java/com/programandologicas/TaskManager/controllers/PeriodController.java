package com.programandologicas.TaskManager.controllers;

import com.programandologicas.TaskManager.dto.RequestPeriod;
import com.programandologicas.TaskManager.dto.ResponsePeriod;
import com.programandologicas.TaskManager.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/periodos")
public class PeriodController {

    @Autowired
    private PeriodService periodService;

    @PostMapping
    public ResponseEntity<ResponsePeriod> crearPeriodo(@Valid @RequestBody RequestPeriod request) {
        ResponsePeriod response = periodService.crearPeriodo(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePeriod> obtenerPeriodoPorId(@PathVariable int id) {
        ResponsePeriod response = periodService.obtenerPeriodoPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ResponsePeriod>> obtenerTodosLosPeriodos() {
        List<ResponsePeriod> response = periodService.obtenerTodosLosPeriodos();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePeriod> actualizarPeriodo(@PathVariable int id, @Valid @RequestBody RequestPeriod request) {
        ResponsePeriod response = periodService.actualizarPeriodo(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPeriodo(@PathVariable int id) {
        periodService.eliminarPeriodo(id);
        return ResponseEntity.noContent().build();
    }
}
