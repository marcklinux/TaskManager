package com.programandologicas.TaskManager.service;

import com.programandologicas.TaskManager.dto.RequestStatus;
import com.programandologicas.TaskManager.dto.ResponseStatus;
import com.programandologicas.TaskManager.dto.StatusMapper;
import com.programandologicas.TaskManager.repository.EstatusRepository;
import com.programandologicas.TaskManager.repository.entities.StatusEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatusService {

    @Autowired
    private EstatusRepository estatusRepository;

    @Autowired
    private StatusMapper statusMapper;

    public ResponseStatus crearStatus(RequestStatus request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del estado es requerido");
        }

        StatusEntity statusEntity = statusMapper.toStatusEntity(request);
        StatusEntity statusGuardado = estatusRepository.save(statusEntity);

        return statusMapper.toResponseStatus(statusGuardado);
    }

    public ResponseStatus obtenerStatusPorId(int id) {
        StatusEntity statusEntity = estatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        return statusMapper.toResponseStatus(statusEntity);
    }

    public List<ResponseStatus> obtenerTodosLosStatus() {
        return estatusRepository.findAll().stream()
                .map(statusMapper::toResponseStatus)
                .collect(Collectors.toList());
    }

    public ResponseStatus actualizarStatus(int id, RequestStatus request) {
        StatusEntity statusEntity = estatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        statusEntity.setName(request.getName());

        StatusEntity statusActualizado = estatusRepository.save(statusEntity);
        return statusMapper.toResponseStatus(statusActualizado);
    }

    public void eliminarStatus(int id) {
        StatusEntity statusEntity = estatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        estatusRepository.delete(statusEntity);
    }
}
