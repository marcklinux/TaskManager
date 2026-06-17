package com.programandologicas.TaskManager.repository;

import com.programandologicas.TaskManager.repository.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Integer> {
    
    @Query("SELECT t FROM TaskEntity t WHERE t.plan.id = :planId")
    List<TaskEntity> obtenerTareasPorPlan(@Param("planId") int planId);
    
    @Query("SELECT t FROM TaskEntity t WHERE t.status.id = :statusId")
    List<TaskEntity> obtenerTareasPorEstatus(@Param("statusId") int statusId);
    
    @Query("SELECT t FROM TaskEntity t WHERE t.taskDate = :fecha")
    List<TaskEntity> obtenerTareasPorFecha(@Param("fecha") LocalDate fecha);
    
    @Query("SELECT t FROM TaskEntity t WHERE t.taskDate BETWEEN :fechaInicio AND :fechaFin ORDER BY t.taskDate ASC")
    List<TaskEntity> obtenerTareasPorRangoFecha(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
}
