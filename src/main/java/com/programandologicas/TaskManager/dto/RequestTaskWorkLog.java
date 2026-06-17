package com.programandologicas.TaskManager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestTaskWorkLog {
    @NotNull(message = "El taskId es requerido")
    private Integer taskId;

    @NotNull(message = "La fecha de trabajo es requerida")
    private LocalDate workDate;

    private String notes;
}

