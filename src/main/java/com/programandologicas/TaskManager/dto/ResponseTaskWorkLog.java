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
public class ResponseTaskWorkLog {
    private int id;
    private int taskId;
    private String taskTitle;
    private int planId;
    private String planTitle;
    private LocalDate workDate;
    private String notes;
}

