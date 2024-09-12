package com.faithjoyfundation.autopilotapi.v1.dto.user_managment;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.auth.Role;
import lombok.Data;

@Data
public class RoleDTO {
    private Long id;
    private String name;

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
