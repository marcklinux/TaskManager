package com.programandologicas.TaskManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanTasksResponse {
    private int planId;
    private LocalDate startDate;
    private LocalDate endDate;
    private StatusResponse status;
    private List<ResponseTask> tasks;
    private int totalTasks;
}
