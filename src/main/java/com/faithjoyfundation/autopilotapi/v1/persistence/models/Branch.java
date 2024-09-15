package com.faithjoyfundation.autopilotapi.v1.persistence.models;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.auth.User;
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
        name = "branches",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "email", name = "UQ_branches_email"),
            @UniqueConstraint(columnNames = "phone", name = "UQ_branches_phone")
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private boolean main;

    @Column(nullable = false)
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
    @JoinColumn(name = "municipality_id", foreignKey = @ForeignKey(name = "FK_branches_municipalities"), nullable = false)
    @JsonIgnoreProperties({"branches", "hibernateLazyInitializer", "handler"})
    private Municipality municipality;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"branch", "hibernateLazyInitializer", "handler"})
    private Set<Car> cars = new HashSet<>();


}
