package com.faithjoyfundation.autopilotapi.v1.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "cars")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String plates;

    @Column(unique = true)
    private String VIN;

    @Column(name = "current_mileage")
    private String currentMileage;

    private Integer year;

    private String color;

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
    @JoinColumn(name = "model_id", foreignKey = @ForeignKey(name = "FK_cars_models"))
    @JsonIgnoreProperties({"cars", "hibernateLazyInitializer", "handler"})
    private Model model;

    @ManyToOne
    @JoinColumn(name = "branch_id", foreignKey = @ForeignKey(name = "FK_cars_branches"))
    @JsonIgnoreProperties({"cars", "hibernateLazyInitializer", "handler"})
    private Branch branch;
}
