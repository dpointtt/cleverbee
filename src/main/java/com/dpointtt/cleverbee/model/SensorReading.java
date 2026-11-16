package com.dpointtt.cleverbee.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "sensor_readings", indexes = {
        @Index(name = "idx_reading_hive_time", columnList = "hive_id, recorded_at")
})
public class SensorReading extends BaseEntity {

    @Column(name = "reading_value", nullable = false)
    private Double value;

    @Column(nullable = false, length = 16)
    private String unit;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hive_id", nullable = false)
    private Hive hive;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;
}
