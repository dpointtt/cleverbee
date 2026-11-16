package com.dpointtt.cleverbee.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "beekeepers")
public class Beekeeper extends BaseEntity {

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @ManyToMany
    @JoinTable(
            name = "beekeeper_apiary",
            joinColumns = @JoinColumn(name = "beekeeper_id"),
            inverseJoinColumns = @JoinColumn(name = "apiary_id")
    )
    private Set<Apiary> apiaries = new HashSet<>();

    @OneToMany(mappedBy = "beekeeper")
    private Set<Inspection> inspections = new HashSet<>();
}
