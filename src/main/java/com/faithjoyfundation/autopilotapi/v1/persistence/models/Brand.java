package com.faithjoyfundation.autopilotapi.v1.persistence.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "brands",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = "UQ_brands_name")
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"brand", "hibernateLazyInitializer", "handler"})
    private Set<Model> models = new HashSet<>();
}