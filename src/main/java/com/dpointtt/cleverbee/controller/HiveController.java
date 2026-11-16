package com.dpointtt.cleverbee.controller;

import com.dpointtt.cleverbee.dto.HiveDto;
import com.dpointtt.cleverbee.service.HiveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hives")
@RequiredArgsConstructor
public class HiveController {

    private final HiveService service;

    @GetMapping
    public List<HiveDto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public HiveDto getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HiveDto create(@Valid @RequestBody HiveDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public HiveDto update(@PathVariable Long id, @Valid @RequestBody HiveDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
