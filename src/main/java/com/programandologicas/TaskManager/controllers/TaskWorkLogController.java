package com.programandologicas.TaskManager.controllers;

import com.programandologicas.TaskManager.dto.RequestTaskWorkLog;
import com.programandologicas.TaskManager.dto.ResponseTaskWorkLog;
import com.programandologicas.TaskManager.dto.WeeklyTaskReportResponse;
import com.programandologicas.TaskManager.service.TaskWorkLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/task-work-logs")
public class TaskWorkLogController {

    @Autowired
    private TaskWorkLogService taskWorkLogService;

    @PostMapping
    public ResponseEntity<ResponseTaskWorkLog> registrarTrabajo(@Valid @RequestBody RequestTaskWorkLog request) {
        return ResponseEntity.ok(taskWorkLogService.registrarTrabajo(request));
    }

    @GetMapping
    public ResponseEntity<List<ResponseTaskWorkLog>> obtenerRegistrosPorRangoFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(taskWorkLogService.obtenerRegistrosPorRangoFecha(fechaInicio, fechaFin));
    }

    @GetMapping("/reporte-semanal")
    public ResponseEntity<WeeklyTaskReportResponse> obtenerReporteSemanal(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(taskWorkLogService.obtenerReporteSemanal(fechaInicio, fechaFin));
    }

    @GetMapping("/reporte-semana/{numeroSemana}")
    public ResponseEntity<WeeklyTaskReportResponse> obtenerReportePorNumeroSemana(@PathVariable int numeroSemana) {
        return ResponseEntity.ok(taskWorkLogService.obtenerReportePorNumeroSemana(numeroSemana));
    }

    @GetMapping("/reporte-semanal/csv")
    public ResponseEntity<byte[]> descargarReporteSemanalCsv(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        byte[] contenido = taskWorkLogService.generarReporteSemanalCsv(fechaInicio, fechaFin);
        String fileName = "reporte-semanal-" + fechaInicio + "-" + fechaFin + ".csv";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/csv"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(contenido);
    }

    @GetMapping("/reporte-semanal/pdf")
    public ResponseEntity<byte[]> descargarReporteSemanalPdf(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        byte[] contenido = taskWorkLogService.generarReporteSemanalPdf(fechaInicio, fechaFin);
        String fileName = "reporte-semanal-" + fechaInicio + "-" + fechaFin + ".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(contenido);
    }
}
