package com.programandologicas.TaskManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestProyect {
    @NotBlank(message = "El nombre del proyecto es requerido")
    private String name;
    private String description;
    @NotNull(message = "El statusId es requerido")
    private Integer statusId;
    private Integer periodId;
    private LocalDate startDate;
    private LocalDate endDate;
}
