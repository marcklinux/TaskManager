package com.programandologicas.TaskManager.repository;

import com.programandologicas.TaskManager.repository.entities.PeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodRepository extends JpaRepository<PeriodEntity,Integer> {
}
