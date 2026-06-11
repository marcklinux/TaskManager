package com.programandologicas.TaskManager.controllers;

import com.programandologicas.TaskManager.dto.RequestStatus;
import com.programandologicas.TaskManager.dto.ResponseStatus;
import com.programandologicas.TaskManager.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    @PostMapping
    public ResponseEntity<ResponseStatus> crearStatus(@Valid @RequestBody RequestStatus request) {
        ResponseStatus response = statusService.crearStatus(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStatus> obtenerStatusPorId(@PathVariable int id) {
        ResponseStatus response = statusService.obtenerStatusPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ResponseStatus>> obtenerTodosLosStatus() {
        List<ResponseStatus> response = statusService.obtenerTodosLosStatus();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStatus> actualizarStatus(@PathVariable int id, @Valid @RequestBody RequestStatus request) {
        ResponseStatus response = statusService.actualizarStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarStatus(@PathVariable int id) {
        statusService.eliminarStatus(id);
        return ResponseEntity.noContent().build();
    }
}
