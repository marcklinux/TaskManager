package com.programandologicas.TaskManager.dto;

import com.programandologicas.TaskManager.repository.entities.TaskEntity;
import com.programandologicas.TaskManager.repository.entities.StatusEntity;
import com.programandologicas.TaskManager.repository.entities.PlanEntity;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    public ResponseTask toResponseTask(TaskEntity entity) {
        return ResponseTask.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .status(toStatusResponse(entity.getStatus()))
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

    public PlanTasksResponse toPlanTasksResponse(PlanEntity plan, List<TaskEntity> tasks) {
        return PlanTasksResponse.builder()
                .planId(plan.getId())
                .startDate(plan.getStartDate())
                .endDate(plan.getEndDate())
                .status(toStatusResponse(plan.getStatus()))
                .tasks(tasks.stream().map(this::toResponseTask).collect(Collectors.toList()))
                .totalTasks(tasks.size())
                .build();
    }
}
