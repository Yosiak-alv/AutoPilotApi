package com.faithjoyfundation.autopilotapi.v1.persistence.models;

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
@ToString(exclude = {"branches", "workShops"})
public class Municipality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id", foreignKey = @ForeignKey(name = "FK_municipalities_departments"), nullable = false)
    @JsonIgnoreProperties({"municipalities", "hibernateLazyInitializer", "handler"})
    private Department department;

    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"municipality", "hibernateLazyInitializer", "handler"})
    private Set<Branch> branches = new HashSet<>();

    @OneToMany(mappedBy = "municipality", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"municipality", "hibernateLazyInitializer", "handler"})
    private Set<WorkShop> workShops = new HashSet<>();
}
