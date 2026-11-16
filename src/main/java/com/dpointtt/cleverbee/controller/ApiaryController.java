package com.dpointtt.cleverbee.controller;

import com.dpointtt.cleverbee.dto.ApiaryDto;
import com.dpointtt.cleverbee.service.ApiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apiaries")
@RequiredArgsConstructor
public class ApiaryController {

    private final ApiaryService service;

    @GetMapping
    public List<ApiaryDto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ApiaryDto getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiaryDto create(@Valid @RequestBody ApiaryDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public ApiaryDto update(@PathVariable Long id, @Valid @RequestBody ApiaryDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}