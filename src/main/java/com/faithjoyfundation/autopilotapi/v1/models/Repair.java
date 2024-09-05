package com.faithjoyfundation.autopilotapi.v1.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "repairs")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Repair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double total;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "workshop_id", foreignKey = @ForeignKey(name = "FK_repairs_workshops"))
    @JsonIgnoreProperties({"repairs", "hibernateLazyInitializer", "handler"})
    private WorkShop workshop;

    @ManyToOne
    @JoinColumn(name = "car_id", foreignKey = @ForeignKey(name = "FK_repairs_cars"))
    @JsonIgnoreProperties({"repairs", "hibernateLazyInitializer", "handler"})
    private Car car;

    @ManyToOne
    @JoinColumn(name = "repair_status_id", foreignKey = @ForeignKey(name = "FK_repairs_repair_statuses"))
    @JsonIgnoreProperties({"repairs", "hibernateLazyInitializer", "handler"})
    private RepairStatus repairStatus;

    @OneToMany(mappedBy = "repair", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"repair", "hibernateLazyInitializer", "handler"})
    private Set<RepairDetail> repairDetails = new HashSet<>();
}
