package com.dpointtt.cleverbee.service;

import com.dpointtt.cleverbee.dto.HiveDto;
import com.dpointtt.cleverbee.model.Hive;
import com.dpointtt.cleverbee.model.Sensor;
import com.dpointtt.cleverbee.repository.ApiaryRepository;
import com.dpointtt.cleverbee.repository.HiveRepository;
import com.dpointtt.cleverbee.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HiveService {

    private final HiveRepository hiveRepository;
    private final ApiaryRepository apiaryRepository;
    private final SensorRepository sensorRepository;

    @Transactional(readOnly = true)
    public List<HiveDto> findAll() {
        return hiveRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public HiveDto findById(Long id) {
        return toDto(getEntity(id));
    }

    @Transactional
    public HiveDto create(HiveDto dto) {
        Hive hive = new Hive();
        apply(hive, dto);
        return toDto(hiveRepository.saveAndFlush(hive));
    }

    @Transactional
    public HiveDto update(Long id, HiveDto dto) {
        Hive hive = getEntity(id);
        apply(hive, dto);
        return toDto(hiveRepository.saveAndFlush(hive));
    }

    @Transactional
    public void delete(Long id) {
        hiveRepository.delete(getEntity(id));
    }

    private Hive getEntity(Long id) {
        return hiveRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Hive not found: " + id));
    }

    private void apply(Hive h, HiveDto d) {
        h.setSerialNumber(d.serialNumber());
        h.setType(d.type());
        h.setStatus(d.status());
        h.setQueenYear(d.queenYear());
        h.setNotes(d.notes());

        h.setApiary(apiaryRepository.findById(d.apiaryId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "Apiary not found: " + d.apiaryId())));

        Set<Long> ids = d.sensorIds() == null ? Set.of() : d.sensorIds();
        List<Sensor> sensors = sensorRepository.findAllById(ids);
        if (sensors.size() != ids.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some sensors not found: " + ids);
        }
        h.getSensors().clear();
        h.getSensors().addAll(sensors);
    }

    private HiveDto toDto(Hive h) {
        return new HiveDto(
                h.getId(), h.getSerialNumber(), h.getType(), h.getStatus(),
                h.getQueenYear(), h.getNotes(),
                h.getApiary().getId(), h.getApiary().getName(),
                h.getSensors().stream().map(Sensor::getId).collect(Collectors.toSet()));
    }
}
