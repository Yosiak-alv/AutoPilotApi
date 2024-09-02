package com.faithjoyfundation.autopilotapi.v1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "repair_details")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class RepairDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "repair_id", foreignKey = @ForeignKey(name = "FK_repair_details_repairs"))
    @JsonIgnoreProperties({"repairDetails", "hibernateLazyInitializer", "handler"})
    private Repair repair;
}
