package com.programandologicas.TaskManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestPlan {
    @NotNull(message = "El proyectId es requerido")
    private Integer proyectId;
    @NotNull(message = "El statusId es requerido")
    private Integer statusId;
    @NotBlank(message = "El título del plan es requerido")
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
