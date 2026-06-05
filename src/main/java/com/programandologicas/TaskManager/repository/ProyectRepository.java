package com.programandologicas.TaskManager.repository;

import com.programandologicas.TaskManager.repository.entities.ProyectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectRepository extends JpaRepository<ProyectEntity, Integer> {
}
