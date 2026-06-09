package com.programandologicas.TaskManager.service;

import com.programandologicas.TaskManager.dto.PlanTasksResponse;
import com.programandologicas.TaskManager.dto.RequestTask;
import com.programandologicas.TaskManager.dto.ResponseTask;
import com.programandologicas.TaskManager.dto.TaskMapper;
import com.programandologicas.TaskManager.repository.EstatusRepository;
import com.programandologicas.TaskManager.repository.PlanRepository;
import com.programandologicas.TaskManager.repository.TaskRepository;
import com.programandologicas.TaskManager.repository.entities.PlanEntity;
import com.programandologicas.TaskManager.repository.entities.StatusEntity;
import com.programandologicas.TaskManager.repository.entities.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private EstatusRepository estatusRepository;

    @Autowired
    private TaskMapper taskMapper;

    public PlanTasksResponse obtenerTareasPorPlan(int planId) {
        PlanEntity plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
        
        List<TaskEntity> tareas = taskRepository.obtenerTareasPorPlan(planId);
        
        return taskMapper.toPlanTasksResponse(plan, tareas);
    }

    public ResponseTask crearTarea(RequestTask request) {
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("El título de la tarea es requerido");
        }

        StatusEntity status = estatusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        PlanEntity plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        TaskEntity taskEntity = taskMapper.toTaskEntity(request, status, plan);
        TaskEntity taskGuardada = taskRepository.save(taskEntity);

        return taskMapper.toResponseTask(taskGuardada);
    }
}

