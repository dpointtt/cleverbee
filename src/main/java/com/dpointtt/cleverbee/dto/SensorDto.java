package com.dpointtt.cleverbee.dto;

import com.dpointtt.cleverbee.model.Sensor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record SensorDto(
        Long id,
        @NotBlank @Size(max = 48) String deviceCode,
        @NotNull Sensor.SensorType type,
        String firmwareVersion,
        Boolean active,
        Set<Long> hiveIds
) {
}
