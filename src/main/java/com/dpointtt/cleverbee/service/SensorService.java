package com.dpointtt.cleverbee.service;

import com.dpointtt.cleverbee.dto.SensorDto;
import com.dpointtt.cleverbee.model.Hive;
import com.dpointtt.cleverbee.model.Sensor;
import com.dpointtt.cleverbee.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository repository;

    @Transactional(readOnly = true)
    public List<SensorDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public SensorDto findById(Long id) {
        return toDto(getEntity(id));
    }

    @Transactional
    public SensorDto create(SensorDto dto) {
        Sensor sensor = new Sensor();
        apply(sensor, dto);
        return toDto(repository.saveAndFlush(sensor));
    }

    @Transactional
    public SensorDto update(Long id, SensorDto dto) {
        Sensor sensor = getEntity(id);
        apply(sensor, dto);
        return toDto(repository.saveAndFlush(sensor));
    }

    @Transactional
    public void delete(Long id) {
        Sensor sensor = getEntity(id);
        for (Hive h : new ArrayList<>(sensor.getHives())) {
            h.getSensors().remove(sensor);
        }
        repository.delete(sensor);
    }

    private Sensor getEntity(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Sensor not found: " + id));
    }

    private void apply(Sensor s, SensorDto d) {
        s.setDeviceCode(d.deviceCode());
        s.setType(d.type());
        s.setFirmwareVersion(d.firmwareVersion());
        s.setActive(d.active() == null || d.active());
    }

    private SensorDto toDto(Sensor s) {
        return new SensorDto(s.getId(), s.getDeviceCode(), s.getType(), s.getFirmwareVersion(),
                s.isActive(),
                s.getHives().stream().map(Hive::getId).collect(Collectors.toSet()));
    }
}