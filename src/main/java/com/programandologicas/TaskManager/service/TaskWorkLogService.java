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

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskWorkLogService {

    private static final LocalDate BASE_SEMANA_1 = LocalDate.of(2026, 6, 15);

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

