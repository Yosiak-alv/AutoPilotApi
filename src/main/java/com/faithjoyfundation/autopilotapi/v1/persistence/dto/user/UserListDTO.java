package com.faithjoyfundation.autopilotapi.v1.persistence.dto.user;

import com.faithjoyfundation.autopilotapi.v1.persistence.models.auth.Role;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.auth.User;
import lombok.Data;

import java.util.Set;

@Data
public class UserListDTO {
    private Long id;
    private String name;
    private String email;
    private Set<Role> roles;

    public UserListDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}
