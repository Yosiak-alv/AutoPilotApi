package com.faithjoyfundation.autopilotapi.v1.persistence.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Column(nullable = false)
    private BigDecimal total;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="workshop_id", foreignKey = @ForeignKey(name = "FK_repairs_workshops"), nullable = false)
    @JsonIgnoreProperties({"repairs", "hibernateLazyInitializer", "handler"})
    private WorkShop workshop;


    @ManyToOne
    @JoinColumn(name = "car_id", foreignKey = @ForeignKey(name = "FK_repairs_cars"), nullable = false)
    @JsonIgnoreProperties({"repairs", "hibernateLazyInitializer", "handler"})
    private Car car;

    @ManyToOne
    @JoinColumn(name = "repair_status_id", foreignKey = @ForeignKey(name = "FK_repairs_repair_statuses"), nullable = false)
    @JsonIgnoreProperties({"repairs", "hibernateLazyInitializer", "handler"})
    private RepairStatus repairStatus;

    @OneToMany(mappedBy = "repair", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"repair", "hibernateLazyInitializer", "handler"})
    private Set<RepairDetail> repairDetails = new HashSet<>();

    public BigDecimal calculateTotal() {
        BigDecimal total = repairDetails.stream()
                .map(repairDetail -> BigDecimal.valueOf(repairDetail.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total.setScale(2, RoundingMode.HALF_UP);
    }
}
