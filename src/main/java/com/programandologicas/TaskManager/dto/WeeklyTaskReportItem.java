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
public class WeeklyTaskReportItem {
    private int taskId;
    private String taskTitle;
    private int planId;
    private String planTitle;
    private int totalRegistros;
    private List<LocalDate> fechasTrabajo;
}

