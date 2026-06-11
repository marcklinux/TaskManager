package com.programandologicas.TaskManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponsePlan {
    private int id;
    private int proyectId;
    private StatusResponse status;
    private LocalDate startDate;
    private LocalDate endDate;
}
