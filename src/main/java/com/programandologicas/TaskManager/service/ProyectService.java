package com.programandologicas.TaskManager.service;

import com.programandologicas.TaskManager.dto.RequestProyect;
import com.programandologicas.TaskManager.dto.ResponseProyect;
import com.programandologicas.TaskManager.dto.ProyectMapper;
import com.programandologicas.TaskManager.repository.ProyectRepository;
import com.programandologicas.TaskManager.repository.EstatusRepository;
import com.programandologicas.TaskManager.repository.PeriodRepository;
import com.programandologicas.TaskManager.repository.entities.ProyectEntity;
import com.programandologicas.TaskManager.repository.entities.StatusEntity;
import com.programandologicas.TaskManager.repository.entities.PeriodEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProyectService {

    @Autowired
    private ProyectRepository proyectRepository;

    @Autowired
    private EstatusRepository estatusRepository;

    @Autowired
    private PeriodRepository periodRepository;

    @Autowired
    private ProyectMapper proyectMapper;

    public ResponseProyect crearProyecto(RequestProyect request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del proyecto es requerido");
        }

        StatusEntity status = estatusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        PeriodEntity period = null;
        if (request.getPeriodId() != null) {
            period = periodRepository.findById(request.getPeriodId())
                    .orElseThrow(() -> new RuntimeException("Período no encontrado"));
        }

        ProyectEntity proyectEntity = proyectMapper.toProyectEntity(request, status, period);
        ProyectEntity proyectGuardado = proyectRepository.save(proyectEntity);

        return proyectMapper.toResponseProyect(proyectGuardado);
    }

    public ResponseProyect obtenerProyectoPorId(int id) {
        ProyectEntity proyectEntity = proyectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        return proyectMapper.toResponseProyect(proyectEntity);
    }

    public List<ResponseProyect> obtenerTodosLosProyectos() {
        return proyectRepository.findAll().stream()
                .map(proyectMapper::toResponseProyect)
                .collect(Collectors.toList());
    }

    public ResponseProyect actualizarProyecto(int id, RequestProyect request) {
        ProyectEntity proyectEntity = proyectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        StatusEntity status = estatusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        PeriodEntity period = null;
        if (request.getPeriodId() != null) {
            period = periodRepository.findById(request.getPeriodId())
                    .orElseThrow(() -> new RuntimeException("Período no encontrado"));
        }

        proyectEntity.setName(request.getName());
        proyectEntity.setDescription(request.getDescription());
        proyectEntity.setStatus(status);
        proyectEntity.setPeriod(period);
        proyectEntity.setStartDate(request.getStartDate());
        proyectEntity.setEndDate(request.getEndDate());

        ProyectEntity proyectActualizado = proyectRepository.save(proyectEntity);
        return proyectMapper.toResponseProyect(proyectActualizado);
    }

    public void eliminarProyecto(int id) {
        ProyectEntity proyectEntity = proyectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        proyectRepository.delete(proyectEntity);
    }
}
