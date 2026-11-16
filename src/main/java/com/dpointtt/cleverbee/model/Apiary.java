package com.dpointtt.cleverbee.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "apiaries")
public class Apiary extends BaseEntity {

    @Column(nullable = false, unique = true, length = 32)
    private String code;

    @Column(nullable = false)
    private String name;

    private String location;

    private Double latitude;
    private Double longitude;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "apiary", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Hive> hives = new HashSet<>();

    @ManyToMany(mappedBy = "apiaries")
    private Set<Beekeeper> beekeepers = new HashSet<>();

    public void addHive(Hive hive) {
        hives.add(hive);
        hive.setApiary(this);
    }
}