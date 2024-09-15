package com.faithjoyfundation.autopilotapi.v1.dto.user_managment;

import com.faithjoyfundation.autopilotapi.v1.dto.branch_managment.BranchDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.auth.Role;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.auth.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;

    private LocalDateTime created;
    private LocalDateTime updated;

    private Set<Role> roles;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.created = user.getCreatedAt();
        this.updated = user.getUpdatedAt();
        this.roles = user.getRoles();
    }
}
