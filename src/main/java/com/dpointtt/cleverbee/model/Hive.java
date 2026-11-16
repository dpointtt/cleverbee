package com.dpointtt.cleverbee.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "hives")
public class Hive extends BaseEntity {

    public enum HiveType { LANGSTROTH, DADANT, WARRE, TOP_BAR }

    public enum HiveStatus { ACTIVE, QUARANTINE, WINTERING, EMPTY }

    @Column(name = "serial_number", nullable = false, unique = true, length = 32)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HiveType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HiveStatus status;

    @Column(name = "queen_year")
    private Integer queenYear;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "apiary_id", nullable = false)
    private Apiary apiary;

    @ManyToMany
    @JoinTable(
            name = "hive_sensor",
            joinColumns = @JoinColumn(name = "hive_id"),
            inverseJoinColumns = @JoinColumn(name = "sensor_id")
    )
    private Set<Sensor> sensors = new HashSet<>();

    @OneToMany(mappedBy = "hive", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SensorReading> readings = new HashSet<>();

    @OneToMany(mappedBy = "hive", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Inspection> inspections = new HashSet<>();
}