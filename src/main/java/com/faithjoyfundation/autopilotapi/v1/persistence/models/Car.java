package com.faithjoyfundation.autopilotapi.v1.persistence.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "cars",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "plates", name = "UQ_brands_plates"),
            @UniqueConstraint(columnNames = "VIN", name = "UQ_brands_VIN"),
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String plates;

    @Column(nullable = false)
    private String VIN;

    @Column(name = "current_mileage", nullable = false)
    private Integer currentMileage;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String motorID;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "model_id", foreignKey = @ForeignKey(name = "FK_cars_models"), nullable = false)
    @JsonIgnoreProperties({"cars", "hibernateLazyInitializer", "handler"})
    private Model model;

    @ManyToOne
    @JoinColumn(name = "branch_id", foreignKey = @ForeignKey(name = "FK_cars_branches"), nullable = false)
    @JsonIgnoreProperties({"cars", "hibernateLazyInitializer", "handler"})
    private Branch branch;
}
