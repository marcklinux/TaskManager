package com.programandologicas.TaskManager.dto;

import com.programandologicas.TaskManager.repository.entities.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TaskMapper {

    public List<ResponseTask> toResponseTaskList(List<TaskEntity> entities) {
        return entities.stream()
                .map(this::toResponseTask)
                .toList();
    }

    public ResponseTask toResponseTask(TaskEntity entity) {
        return ResponseTask.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(toStatusResponse(entity.getStatus()))
                .plan(toPlanResponse(entity.getPlan()))
                .taskDate(entity.getTaskDate())
                .build();
    }

    public TaskEntity toTaskEntity(RequestTask request, StatusEntity status, PlanEntity plan) {
        return TaskEntity.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(status)
                .plan(plan)
                .taskDate(request.getTaskDate())
                .build();
    }

    public StatusResponse toStatusResponse(StatusEntity entity) {
        return StatusResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public ResponsePlan toPlanResponse(PlanEntity plan) {
        return ResponsePlan.builder()
                .id(plan.getId())
                .proyect(toResponseProyect(plan.getProyect()))
                .status(toStatusResponse(plan.getStatus()))
                .title(plan.getTitle())
                .description(plan.getDescription())
                .startDate(plan.getStartDate())
                .endDate(plan.getEndDate())
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
}
