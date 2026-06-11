package com.programandologicas.TaskManager.dto;

import com.programandologicas.TaskManager.repository.entities.ProyectEntity;
import com.programandologicas.TaskManager.repository.entities.StatusEntity;
import com.programandologicas.TaskManager.repository.entities.PeriodEntity;
import org.springframework.stereotype.Component;

@Component
public class ProyectMapper {

    public ResponseProyect toResponseProyect(ProyectEntity entity) {
        return ResponseProyect.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(toStatusResponse(entity.getStatus()))
                .period(entity.getPeriod() != null ? toPeriodResponse(entity.getPeriod()) : null)
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }

    public ProyectEntity toProyectEntity(RequestProyect request, StatusEntity status, PeriodEntity period) {
        return ProyectEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(status)
                .period(period)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
    }

    private StatusResponse toStatusResponse(StatusEntity entity) {
        return StatusResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    private ResponsePeriod toPeriodResponse(PeriodEntity entity) {
        return ResponsePeriod.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
