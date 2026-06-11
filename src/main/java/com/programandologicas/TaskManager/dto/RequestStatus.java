package com.programandologicas.TaskManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestStatus {
    @NotBlank(message = "El nombre del estado es requerido")
    private String name;
}
