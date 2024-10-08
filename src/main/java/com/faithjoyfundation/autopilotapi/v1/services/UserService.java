package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.user.UserDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.user.UserListDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.user.UserRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.auth.User;

import java.security.Principal;

public interface UserService {
    PaginatedResponse<UserListDTO> findAllBySearch(String search, int page, int size);

    UserDTO findDTOById(Long id);

    User findModelById(Long id);

    UserDTO create(UserRequest userRequest);

    UserDTO update(Long id, UserRequest userRequest, Principal principal);

    boolean resetTempPassword(Long id);

    boolean delete(Long id, Principal principal);
}
