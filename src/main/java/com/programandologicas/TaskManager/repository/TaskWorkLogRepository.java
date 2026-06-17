package com.programandologicas.TaskManager.repository;

import com.programandologicas.TaskManager.repository.entities.TaskWorkLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskWorkLogRepository extends JpaRepository<TaskWorkLogEntity, Integer> {

    @Query("SELECT l FROM TaskWorkLogEntity l JOIN FETCH l.task t JOIN FETCH t.plan WHERE l.workDate BETWEEN :fechaInicio AND :fechaFin ORDER BY l.workDate ASC")
    List<TaskWorkLogEntity> obtenerRegistrosPorRangoFecha(@Param("fechaInicio") LocalDate fechaInicio,
                                                          @Param("fechaFin") LocalDate fechaFin);
}

