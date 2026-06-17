package com.programandologicas.TaskManager.service;

import com.programandologicas.TaskManager.dto.RequestTaskWorkLog;
import com.programandologicas.TaskManager.dto.ResponseTaskWorkLog;
import com.programandologicas.TaskManager.dto.WeeklyTaskReportItem;
import com.programandologicas.TaskManager.dto.WeeklyTaskReportResponse;
import com.programandologicas.TaskManager.repository.TaskRepository;
import com.programandologicas.TaskManager.repository.TaskWorkLogRepository;
import com.programandologicas.TaskManager.repository.entities.PlanEntity;
import com.programandologicas.TaskManager.repository.entities.TaskEntity;
import com.programandologicas.TaskManager.repository.entities.TaskWorkLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskWorkLogService {

    private static final LocalDate BASE_SEMANA_1 = LocalDate.of(2026, 6, 15);
    private static final DateTimeFormatter PDF_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final int PDF_MAX_CHARS_PER_LINE = 90;

    @Autowired
    private TaskWorkLogRepository taskWorkLogRepository;

    @Autowired
    private TaskRepository taskRepository;

    public ResponseTaskWorkLog registrarTrabajo(RequestTaskWorkLog request) {
        if (request.getTaskId() == null || request.getWorkDate() == null) {
            throw new IllegalArgumentException("taskId y workDate son requeridos");
        }

        TaskEntity task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        validarFechaDentroDelPlan(task, request.getWorkDate());

        TaskWorkLogEntity entity = TaskWorkLogEntity.builder()
                .task(task)
                .workDate(request.getWorkDate())
                .notes(request.getNotes())
                .build();

        TaskWorkLogEntity guardado = taskWorkLogRepository.save(entity);
        return toResponse(guardado);
    }

    public List<ResponseTaskWorkLog> obtenerRegistrosPorRangoFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        validarRango(fechaInicio, fechaFin);
        return taskWorkLogRepository.obtenerRegistrosPorRangoFecha(fechaInicio, fechaFin)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public WeeklyTaskReportResponse obtenerReporteSemanal(LocalDate fechaInicio, LocalDate fechaFin) {
        validarRango(fechaInicio, fechaFin);
        List<TaskWorkLogEntity> registros = taskWorkLogRepository.obtenerRegistrosPorRangoFecha(fechaInicio, fechaFin);

        Map<Integer, WeeklyTaskReportItem> agrupado = new LinkedHashMap<>();
        for (TaskWorkLogEntity registro : registros) {
            TaskEntity task = registro.getTask();
            PlanEntity plan = task.getPlan();

            WeeklyTaskReportItem item = agrupado.computeIfAbsent(task.getId(), key -> WeeklyTaskReportItem.builder()
                    .taskId(task.getId())
                    .taskTitle(task.getTitle())
                    .planId(plan.getId())
                    .planTitle(plan.getTitle())
                    .totalRegistros(0)
                    .fechasTrabajo(new java.util.ArrayList<>())
                    .build());

            item.getFechasTrabajo().add(registro.getWorkDate());
            item.setTotalRegistros(item.getTotalRegistros() + 1);
        }

        List<WeeklyTaskReportItem> tareas = agrupado.values().stream()
                .peek(item -> item.getFechasTrabajo().sort(Comparator.naturalOrder()))
                .toList();

        return WeeklyTaskReportResponse.builder()
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .totalRegistros(registros.size())
                .tareas(tareas)
                .build();
    }

    public WeeklyTaskReportResponse obtenerReportePorNumeroSemana(int numeroSemana) {
        if (numeroSemana <= 0) {
            throw new IllegalArgumentException("numeroSemana debe ser mayor a 0");
        }

        LocalDate fechaInicio = BASE_SEMANA_1.plusWeeks(numeroSemana - 1L);
        LocalDate fechaFin = fechaInicio.plusDays(6);
        return obtenerReporteSemanal(fechaInicio, fechaFin);
    }

    public byte[] generarReporteSemanalCsv(LocalDate fechaInicio, LocalDate fechaFin) {
        WeeklyTaskReportResponse reporte = obtenerReporteSemanal(fechaInicio, fechaFin);

        StringBuilder sb = new StringBuilder();
        sb.append("fecha_inicio,fecha_fin,total_registros\n");
        sb.append(reporte.getFechaInicio()).append(",")
                .append(reporte.getFechaFin()).append(",")
                .append(reporte.getTotalRegistros()).append("\n\n");

        sb.append("task_id,tarea,plan_id,plan,total_registros,fechas_trabajo\n");
        for (WeeklyTaskReportItem item : reporte.getTareas()) {
            String fechas = item.getFechasTrabajo().stream()
                    .map(LocalDate::toString)
                    .collect(Collectors.joining("|"));

            sb.append(item.getTaskId()).append(",")
                    .append(escapeCsv(item.getTaskTitle())).append(",")
                    .append(item.getPlanId()).append(",")
                    .append(escapeCsv(item.getPlanTitle())).append(",")
                    .append(item.getTotalRegistros()).append(",")
                    .append(escapeCsv(fechas)).append("\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    public byte[] generarReporteSemanalPdf(LocalDate fechaInicio, LocalDate fechaFin) {
        WeeklyTaskReportResponse reporte = obtenerReporteSemanal(fechaInicio, fechaFin);

        List<String> lines = new java.util.ArrayList<>();
        lines.add("");
        lines.add("================================================================================");
        lines.add("                        REPORTE SEMANAL DE TRABAJO");
        lines.add("================================================================================");
        lines.add("");
        lines.add("Periodo: " + formatDate(reporte.getFechaInicio()) + " a " + formatDate(reporte.getFechaFin()));
        lines.add("Total de registros en la semana: " + reporte.getTotalRegistros());
        lines.add("");
        lines.add("================================================================================");
        lines.add("");
        lines.add("DETALLE DE TAREAS TRABAJADAS:");
        lines.add("");
        lines.add("┌─────────┬──────────────────────────────┬──────────┬─────────────────────────┐");
        lines.add("│ Task ID │ Nombre de Tarea              │ Registros│ Fechas Trabajadas       │");
        lines.add("├─────────┼──────────────────────────────┼──────────┼─────────────────────────┤");

        for (WeeklyTaskReportItem item : reporte.getTareas()) {
            String fechas = item.getFechasTrabajo().stream()
                    .map(this::formatDate)
                    .collect(Collectors.joining(", "));

            String taskName = padRight(item.getTaskTitle(), 28);
            String records = padRight(String.valueOf(item.getTotalRegistros()), 8);
            String taskDates = padRight(fechas.substring(0, Math.min(23, fechas.length())), 23);
            String taskId = padRight(String.valueOf(item.getTaskId()), 7);

            lines.add("│ " + taskId + " │ " + taskName + " │ " + records + " │ " + taskDates + " │");
            lines.add("│         │ Plan: " + padRight(item.getPlanTitle().substring(0, Math.min(22, item.getPlanTitle().length())), 22) + " │          │                         │");
            lines.add("");
        }
        lines.add("└─────────┴──────────────────────────────┴──────────┴─────────────────────────┘");
        lines.add("");
        lines.add("================================================================================");
        lines.add("Generado: " + LocalDate.now().format(PDF_DATE_FORMAT));
        lines.add("================================================================================");

        return buildSimplePdf(lines);
    }

    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        String escaped = value.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }

    private String formatDate(LocalDate date) {
        return date == null ? "" : date.format(PDF_DATE_FORMAT);
    }

    private String padRight(String text, int length) {
        if (text == null) {
            text = "";
        }
        return text.length() >= length ? text.substring(0, length) : text + " ".repeat(length - text.length());
    }

    private byte[] buildSimplePdf(List<String> lines) {
        try {
            List<String> normalizedLines = lines.stream()
                    .flatMap(line -> wrapPlainText(line).stream())
                    .toList();

            StringBuilder content = new StringBuilder();
            content.append("BT\n/F1 12 Tf\n14 TL\n50 760 Td\n");

            boolean first = true;
            for (String line : normalizedLines) {
                if (!first) {
                    content.append("T*\n");
                }
                content.append("(").append(escapePdfText(line)).append(") Tj\n");
                first = false;
            }
            content.append("ET");

            byte[] contentBytes = content.toString().getBytes(StandardCharsets.US_ASCII);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            List<Integer> offsets = new java.util.ArrayList<>();

            writeAscii(out, "%PDF-1.4\n");

            offsets.add(out.size());
            writeAscii(out, "1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n");

            offsets.add(out.size());
            writeAscii(out, "2 0 obj\n<< /Type /Pages /Count 1 /Kids [3 0 R] >>\nendobj\n");

            offsets.add(out.size());
            writeAscii(out, "3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] /Resources << /Font << /F1 4 0 R >> >> /Contents 5 0 R >>\nendobj\n");

            offsets.add(out.size());
            writeAscii(out, "4 0 obj\n<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>\nendobj\n");

            offsets.add(out.size());
            writeAscii(out, "5 0 obj\n<< /Length " + contentBytes.length + " >>\nstream\n");
            out.write(contentBytes);
            writeAscii(out, "\nendstream\nendobj\n");

            int xrefOffset = out.size();
            writeAscii(out, "xref\n0 6\n");
            writeAscii(out, "0000000000 65535 f \n");
            for (Integer offset : offsets) {
                writeAscii(out, String.format(java.util.Locale.ROOT, "%010d 00000 n \n", offset));
            }
            writeAscii(out, "trailer\n<< /Size 6 /Root 1 0 R >>\nstartxref\n" + xrefOffset + "\n%%EOF");
            return out.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo generar el PDF del reporte semanal", ex);
        }
    }

    private List<String> wrapPlainText(String text) {
        if (text == null || text.isEmpty()) {
            return java.util.List.of("");
        }

        List<String> lines = new java.util.ArrayList<>();
        String[] words = text.split("\\s+");
        StringBuilder current = new StringBuilder();

        for (String word : words) {
            String candidate = current.isEmpty() ? word : current + " " + word;
            if (candidate.length() > PDF_MAX_CHARS_PER_LINE && !current.isEmpty()) {
                lines.add(current.toString());
                current = new StringBuilder(word);
            } else {
                current = new StringBuilder(candidate);
            }
        }

        if (!current.isEmpty()) {
            lines.add(current.toString());
        }
        return lines;
    }

    private String escapePdfText(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("(", "\\(")
                .replace(")", "\\)");
    }

    private void writeAscii(ByteArrayOutputStream out, String value) throws java.io.IOException {
        out.write(value.getBytes(StandardCharsets.US_ASCII));
    }

    private void validarRango(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("fechaInicio y fechaFin son requeridas");
        }
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("fechaInicio no puede ser mayor que fechaFin");
        }
    }

    private void validarFechaDentroDelPlan(TaskEntity task, LocalDate workDate) {
        LocalDate planInicio = task.getPlan().getStartDate();
        LocalDate planFin = task.getPlan().getEndDate();

        if (planInicio != null && workDate.isBefore(planInicio)) {
            throw new IllegalArgumentException("La fecha de trabajo es anterior al inicio del plan");
        }
        if (planFin != null && workDate.isAfter(planFin)) {
            throw new IllegalArgumentException("La fecha de trabajo es posterior al fin del plan");
        }
    }

    private ResponseTaskWorkLog toResponse(TaskWorkLogEntity entity) {
        return ResponseTaskWorkLog.builder()
                .id(entity.getId())
                .taskId(entity.getTask().getId())
                .taskTitle(entity.getTask().getTitle())
                .planId(entity.getTask().getPlan().getId())
                .planTitle(entity.getTask().getPlan().getTitle())
                .workDate(entity.getWorkDate())
                .notes(entity.getNotes())
                .build();
    }
}
