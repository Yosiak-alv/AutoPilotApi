package com.faithjoyfundation.autopilotapi.v1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "municipalities")
@Getter
@Setter
@ToString
public class Municipality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id", foreignKey = @ForeignKey(name = "FK_municipalities_departments"))
    @JsonIgnoreProperties({"municipalities", "hibernateLazyInitializer", "handler"})
    private Department department;

    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"municipality", "hibernateLazyInitializer", "handler"})
    private Set<Branch> branches = new HashSet<>();

    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"municipality", "hibernateLazyInitializer", "handler"})
    private Set<WorkShop> workShops = new HashSet<>();
}
