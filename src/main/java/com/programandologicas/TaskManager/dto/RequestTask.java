package com.programandologicas.TaskManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestTask {
    @NotBlank(message = "El título es requerido")
    private String title;
    private String description;
    @NotNull(message = "El statusId es requerido")
    private Integer statusId;
    @NotNull(message = "El planId es requerido")
    private Integer planId;
    private LocalDate taskDate;
}
