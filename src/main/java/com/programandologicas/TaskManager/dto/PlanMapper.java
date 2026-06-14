package com.programandologicas.TaskManager.dto;

import com.programandologicas.TaskManager.repository.entities.PeriodEntity;
import com.programandologicas.TaskManager.repository.entities.PlanEntity;
import com.programandologicas.TaskManager.repository.entities.ProyectEntity;
import com.programandologicas.TaskManager.repository.entities.StatusEntity;
import org.springframework.stereotype.Component;

@Component
public class PlanMapper {

    public ResponsePlan toResponsePlan(PlanEntity entity) {
        return ResponsePlan.builder()
                .id(entity.getId())
                .proyect(toResponseProyect(entity.getProyect()))
                .status(toStatusResponse(entity.getStatus()))
                .title(entity.getTitle())
                .description(entity.getDescription())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }

    private ResponseProyect toResponseProyect(ProyectEntity proyect) {
        return ResponseProyect.builder()
                .id(proyect.getId())
                .name(proyect.getName())
                .description(proyect.getDescription())
                .status(toStatusResponse(proyect.getStatus()))
                .period(toResponsePeriod(proyect.getPeriod()))
                .startDate(proyect.getStartDate())
                .endDate(proyect.getEndDate())
                .build();
    }

    private ResponsePeriod toResponsePeriod(PeriodEntity period) {
        return ResponsePeriod.builder()
                .id(period.getId())
                .name(period.getName())
                .description(period.getDescription())
                .build();
    }

    public PlanEntity toPlanEntity(RequestPlan request, StatusEntity status) {
        PlanEntity plan = new PlanEntity();
        plan.setStatus(status);
        plan.setTitle(request.getTitle());
        plan.setDescription(request.getDescription());
        plan.setStartDate(request.getStartDate());
        plan.setEndDate(request.getEndDate());
        return plan;
    }

    private StatusResponse toStatusResponse(StatusEntity entity) {
        return StatusResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
