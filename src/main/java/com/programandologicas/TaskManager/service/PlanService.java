package com.programandologicas.TaskManager.service;

import com.programandologicas.TaskManager.dto.RequestPlan;
import com.programandologicas.TaskManager.dto.ResponsePlan;
import com.programandologicas.TaskManager.dto.PlanMapper;
import com.programandologicas.TaskManager.repository.PlanRepository;
import com.programandologicas.TaskManager.repository.ProyectRepository;
import com.programandologicas.TaskManager.repository.EstatusRepository;
import com.programandologicas.TaskManager.repository.entities.PlanEntity;
import com.programandologicas.TaskManager.repository.entities.ProyectEntity;
import com.programandologicas.TaskManager.repository.entities.StatusEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ProyectRepository proyectRepository;

    @Autowired
    private EstatusRepository estatusRepository;

    @Autowired
    private PlanMapper planMapper;

    public ResponsePlan crearPlan(RequestPlan request) {
        if (request.getProyectId() == null) {
            throw new IllegalArgumentException("El proyectId es requerido");
        }

        ProyectEntity proyect = proyectRepository.findById(request.getProyectId())
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        StatusEntity status = estatusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        PlanEntity planEntity = planMapper.toPlanEntity(request, status);
        planEntity.setProyect(proyect);
        PlanEntity planGuardado = planRepository.save(planEntity);

        return planMapper.toResponsePlan(planGuardado);
    }

    public ResponsePlan obtenerPlanPorId(int id) {
        PlanEntity planEntity = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
        return planMapper.toResponsePlan(planEntity);
    }

    public List<ResponsePlan> obtenerTodosLosPlanes() {
        return planRepository.findAll().stream()
                .map(planMapper::toResponsePlan)
                .collect(Collectors.toList());
    }

    public List<ResponsePlan> obtenerPlanesPorProyecto(int proyectId) {
        ProyectEntity proyect = proyectRepository.findById(proyectId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        
        return planRepository.findAll().stream()
                .filter(plan -> plan.getProyect().getId() == proyectId)
                .map(planMapper::toResponsePlan)
                .collect(Collectors.toList());
    }

    public ResponsePlan actualizarPlan(int id, RequestPlan request) {
        PlanEntity planEntity = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));

        StatusEntity status = estatusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        planEntity.setStatus(status);
        planEntity.setStartDate(request.getStartDate());
        planEntity.setEndDate(request.getEndDate());

        PlanEntity planActualizado = planRepository.save(planEntity);
        return planMapper.toResponsePlan(planActualizado);
    }

    public void eliminarPlan(int id) {
        PlanEntity planEntity = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
        planRepository.delete(planEntity);
    }
}
