package com.programandologicas.TaskManager.dto;

import com.programandologicas.TaskManager.repository.entities.PeriodEntity;
import org.springframework.stereotype.Component;

@Component
public class PeriodMapper {

    public ResponsePeriod toResponsePeriod(PeriodEntity entity) {
        return ResponsePeriod.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public PeriodEntity toPeriodEntity(RequestPeriod request) {
        PeriodEntity period = new PeriodEntity();
        period.setName(request.getName());
        period.setDescription(request.getDescription());
        return period;
    }
}
