package com.faithjoyfundation.autopilotapi.v1.models;

import com.faithjoyfundation.autopilotapi.v1.models.auth.User;
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
@Table(name = "branches")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private boolean main;

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
    @JoinColumn(name = "municipality_id", foreignKey = @ForeignKey(name = "FK_branches_municipalities"))
    @JsonIgnoreProperties({"branches", "hibernateLazyInitializer", "handler"})
    private Municipality municipality;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"branch", "hibernateLazyInitializer", "handler"})
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"branch", "hibernateLazyInitializer", "handler"})
    private Set<Car> cars = new HashSet<>();


}
