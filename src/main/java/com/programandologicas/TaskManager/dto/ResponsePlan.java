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
    private ResponseProyect proyect;
    private StatusResponse status;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
