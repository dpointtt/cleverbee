package com.dpointtt.cleverbee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ApiaryDto(
        Long id,
        @NotBlank @Size(max = 32) String code,
        @NotBlank String name,
        String location,
        Double latitude,
        Double longitude,
        String description
) {
}
