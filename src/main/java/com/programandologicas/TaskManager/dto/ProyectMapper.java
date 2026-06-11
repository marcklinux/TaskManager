package com.programandologicas.TaskManager.dto;

import com.programandologicas.TaskManager.repository.entities.ProyectEntity;
import com.programandologicas.TaskManager.repository.entities.StatusEntity;
import org.springframework.stereotype.Component;

@Component
public class ProyectMapper {

    public ResponseProyect toResponseProyect(ProyectEntity entity) {
        return ResponseProyect.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .status(toStatusResponse(entity.getStatus()))
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }

    public ProyectEntity toProyectEntity(RequestProyect request, StatusEntity status) {
        return ProyectEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(status)
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
}
