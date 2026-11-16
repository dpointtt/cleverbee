package com.dpointtt.cleverbee.dto;

import com.dpointtt.cleverbee.model.Hive;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record HiveDto(
        Long id,
        @NotBlank @Size(max = 32) String serialNumber,
        @NotNull Hive.HiveType type,
        @NotNull Hive.HiveStatus status,
        Integer queenYear,
        String notes,
        @NotNull Long apiaryId,
        String apiaryName,
        Set<Long> sensorIds
) {
}
