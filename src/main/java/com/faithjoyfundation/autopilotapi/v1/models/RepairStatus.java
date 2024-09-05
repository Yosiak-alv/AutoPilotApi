package com.faithjoyfundation.autopilotapi.v1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "repair_statuses")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RepairStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "repairStatus", cascade = CascadeType.ALL)
    //@JsonIgnoreProperties({"repairStatus", "repairDetails","car", "repairs", "hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private Set<Repair> repairs = new HashSet<>();
}
