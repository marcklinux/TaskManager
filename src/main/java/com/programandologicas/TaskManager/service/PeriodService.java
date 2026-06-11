package com.programandologicas.TaskManager.service;

import com.programandologicas.TaskManager.dto.RequestPeriod;
import com.programandologicas.TaskManager.dto.ResponsePeriod;
import com.programandologicas.TaskManager.dto.PeriodMapper;
import com.programandologicas.TaskManager.repository.PeriodRepository;
import com.programandologicas.TaskManager.repository.entities.PeriodEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeriodService {

    @Autowired
    private PeriodRepository periodRepository;

    @Autowired
    private PeriodMapper periodMapper;

    public ResponsePeriod crearPeriodo(RequestPeriod request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del período es requerido");
        }

        PeriodEntity periodEntity = periodMapper.toPeriodEntity(request);
        PeriodEntity periodGuardado = periodRepository.save(periodEntity);

        return periodMapper.toResponsePeriod(periodGuardado);
    }

    public ResponsePeriod obtenerPeriodoPorId(int id) {
        PeriodEntity periodEntity = periodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Período no encontrado"));
        return periodMapper.toResponsePeriod(periodEntity);
    }

    public List<ResponsePeriod> obtenerTodosLosPeriodos() {
        return periodRepository.findAll().stream()
                .map(periodMapper::toResponsePeriod)
                .collect(Collectors.toList());
    }

    public ResponsePeriod actualizarPeriodo(int id, RequestPeriod request) {
        PeriodEntity periodEntity = periodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Período no encontrado"));

        periodEntity.setName(request.getName());
        periodEntity.setDescription(request.getDescription());

        PeriodEntity periodActualizado = periodRepository.save(periodEntity);
        return periodMapper.toResponsePeriod(periodActualizado);
    }

    public void eliminarPeriodo(int id) {
        PeriodEntity periodEntity = periodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Período no encontrado"));
        periodRepository.delete(periodEntity);
    }
}
