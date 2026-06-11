package com.programandologicas.TaskManager.dto;

import com.programandologicas.TaskManager.repository.entities.StatusEntity;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper {

    public ResponseStatus toResponseStatus(StatusEntity entity) {
        return ResponseStatus.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public StatusEntity toStatusEntity(RequestStatus request) {
        StatusEntity status = new StatusEntity();
        status.setName(request.getName());
        return status;
    }
}
