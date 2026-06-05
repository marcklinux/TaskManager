package com.programandologicas.TaskManager.repository;

import com.programandologicas.TaskManager.repository.entities.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstatusRepository extends JpaRepository<StatusEntity,Integer> {
}
