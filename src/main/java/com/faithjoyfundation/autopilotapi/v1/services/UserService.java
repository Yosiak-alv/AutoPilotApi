package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.user_managment.UserDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.user_managment.UserListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.user_managment.UserRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.auth.User;

public interface UserService {
    PaginatedResponse<UserListDTO> findAllBySearch(String search, int page, int size);

    UserDTO findDTOById(Long id);

    User findModelById(Long id);

    UserDTO create(UserRequest userRequest);

    UserDTO update(Long id, UserRequest userRequest);

    boolean delete(Long id);
}
