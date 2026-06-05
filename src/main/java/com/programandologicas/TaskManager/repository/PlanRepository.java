package com.programandologicas.TaskManager.repository;

import com.programandologicas.TaskManager.repository.entities.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<PlanEntity,Integer> {
}
