package com.dpointtt.cleverbee.service;

import com.dpointtt.cleverbee.dto.ApiaryDto;
import com.dpointtt.cleverbee.model.Apiary;
import com.dpointtt.cleverbee.model.Beekeeper;
import com.dpointtt.cleverbee.repository.ApiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiaryService {

    private final ApiaryRepository repository;

    @Transactional(readOnly = true)
    public List<ApiaryDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ApiaryDto findById(Long id) {
        return toDto(getEntity(id));
    }

    @Transactional
    public ApiaryDto create(ApiaryDto dto) {
        Apiary apiary = new Apiary();
        apply(apiary, dto);
        return toDto(repository.saveAndFlush(apiary));
    }

    @Transactional
    public ApiaryDto update(Long id, ApiaryDto dto) {
        Apiary apiary = getEntity(id);
        apply(apiary, dto);
        return toDto(repository.saveAndFlush(apiary));
    }

    @Transactional
    public void delete(Long id) {
        Apiary apiary = getEntity(id);
        for (Beekeeper b : new ArrayList<>(apiary.getBeekeepers())) {
            b.getApiaries().remove(apiary);
        }
        repository.delete(apiary);
    }

    private Apiary getEntity(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Apiary not found: " + id));
    }

    private void apply(Apiary a, ApiaryDto d) {
        a.setCode(d.code());
        a.setName(d.name());
        a.setLocation(d.location());
        a.setLatitude(d.latitude());
        a.setLongitude(d.longitude());
        a.setDescription(d.description());
    }

    private ApiaryDto toDto(Apiary a) {
        return new ApiaryDto(a.getId(), a.getCode(), a.getName(), a.getLocation(),
                a.getLatitude(), a.getLongitude(), a.getDescription());
    }
}