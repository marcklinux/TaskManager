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
public class ResponseTask {
    private int id;
    private String title;
    private String description;
    private StatusResponse status;
    private ResponsePlan plan;
    private LocalDate taskDate;
}
