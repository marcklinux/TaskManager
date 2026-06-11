package com.programandologicas.TaskManager.dto;

import com.programandologicas.TaskManager.repository.entities.PlanEntity;
import com.programandologicas.TaskManager.repository.entities.StatusEntity;
import org.springframework.stereotype.Component;

@Component
public class PlanMapper {

    public ResponsePlan toResponsePlan(PlanEntity entity) {
        return ResponsePlan.builder()
                .id(entity.getId())
                .proyectId(entity.getProyect().getId())
                .status(toStatusResponse(entity.getStatus()))
                .title(entity.getTitle())
                .description(entity.getDescription())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
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
