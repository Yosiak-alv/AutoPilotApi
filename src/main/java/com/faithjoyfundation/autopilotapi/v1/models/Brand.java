package com.faithjoyfundation.autopilotapi.v1.models;

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

    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"brand", "hibernateLazyInitializer", "handler"})
    private Set<Model> models = new HashSet<>();

    @Override
    public int hashCode() {
        int result = 31;
        result = 31 * result + ((id == null) ? 0 : id.hashCode());
        result = 31 * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Brand brand = (Brand) obj;
        return (id == null ? brand.id == null : id.equals(brand.id)) &&
                (name == null ? brand.name == null : name.equals(brand.name));
    }

}