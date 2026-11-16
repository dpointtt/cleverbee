package com.dpointtt.cleverbee.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "inspections")
public class Inspection extends BaseEntity {

    @Column(name = "inspected_at", nullable = false)
    private LocalDateTime inspectedAt;

    @Column(name = "colony_strength")
    private Integer colonyStrength; // 1-10

    @Column(name = "queen_seen", nullable = false)
    private boolean queenSeen;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hive_id", nullable = false)
    private Hive hive;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "beekeeper_id", nullable = false)
    private Beekeeper beekeeper;
}
