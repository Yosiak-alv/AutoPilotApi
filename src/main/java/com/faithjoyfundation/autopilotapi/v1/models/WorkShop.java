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
@Table(
        name = "workshops",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email", name = "UQ_workshops_email"),
                @UniqueConstraint(columnNames = "phone", name = "UQ_workshops_phone")
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class WorkShop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String address;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "municipality_id", foreignKey = @ForeignKey(name = "FK_workshops_municipalities"))
    @JsonIgnoreProperties({"workshops", "hibernateLazyInitializer", "handler"})
    private Municipality municipality;

    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"workshop", "hibernateLazyInitializer", "handler"})
    private Set<Repair> repairs = new HashSet<>();
}
