package com.faithjoyfundation.autopilotapi.v1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "models")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id", foreignKey = @ForeignKey(name = "FK_models_brands"))
    @JsonIgnoreProperties({"models", "hibernateLazyInitializer", "handler"})
    private Brand brand;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"model", "hibernateLazyInitializer", "handler"})
    private Set<Car> cars = new HashSet<>();
}
