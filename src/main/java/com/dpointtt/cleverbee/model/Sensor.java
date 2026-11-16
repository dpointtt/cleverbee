package com.dpointtt.cleverbee.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sensors")
public class Sensor extends BaseEntity {

    public enum SensorType { WEIGHT, TEMPERATURE, HUMIDITY, ACOUSTIC }

    @Column(name = "device_code", nullable = false, unique = true, length = 48)
    private String deviceCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SensorType type;

    @Column(name = "firmware_version", length = 16)
    private String firmwareVersion;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToMany(mappedBy = "sensors")
    private Set<Hive> hives = new HashSet<>();

    @OneToMany(mappedBy = "sensor")
    private Set<SensorReading> readings = new HashSet<>();
}
