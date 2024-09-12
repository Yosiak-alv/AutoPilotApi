package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.dto.user_managment.RoleRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.user_managment.UserDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.user_managment.UserListDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.user_managment.UserRequest;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.BadRequestException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Branch;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.auth.Role;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.auth.User;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.RoleRepository;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.UserRepository;
import com.faithjoyfundation.autopilotapi.v1.services.BranchService;
import com.faithjoyfundation.autopilotapi.v1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BranchService branchService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PaginatedResponse<UserListDTO> findAllBySearch(String search, int page, int size) {
        validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size);
        return new PaginatedResponse<>(userRepository.findAllBySearch(search, pageable).map(UserListDTO::new));
    }

    @Override
    public UserDTO findDTOById(Long id) {
        return new UserDTO(findModelById(id));
    }

    @Override
    public User findModelById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    @Override
    public UserDTO create(UserRequest userRequest) {
        Branch branch = branchService.findModelById(userRequest.getBranchId());
        List<Role> roles = getRoles(userRequest.getRoles().stream().map(RoleRequest::getId).toList());

        validateUniqueEmail(userRequest.getEmail(), null);
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setBranch(branch);
        user.setRoles(new HashSet<>(roles));
        String password = "password"; //TODO generate random password and send email
        user.setPassword(passwordEncoder.encode(password));
        return new UserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO update(Long id, UserRequest userRequest) {
        User user = findModelById(id);
        List<Role> roles = getRoles(userRequest.getRoles().stream().map(RoleRequest::getId).toList());
        validateUniqueEmail(userRequest.getEmail(), id);

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setBranch(branchService.findModelById(userRequest.getBranchId()));
        user.setRoles(new HashSet<>(roles));
        return new UserDTO(userRepository.save(user));
    }

    @Override
    public boolean delete(Long id) {
        User user = findModelById(id);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    private List<Role> getRoles(List<Long> roleIds) {
        List<Role> roles = new ArrayList<>();
        for (Long roleId : roleIds) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
            roles.add(role);
        }
        return roles;
    }

    private void validateUniqueEmail(String email, Long existingId) throws BadRequestException {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent() && !existingUser.get().getId().equals(existingId)) {
            throw new BadRequestException("User with email " + email + " already exists.");
        }
    }

    private void validatePageNumberAndSize(int page, int size) throws BadRequestException {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }
        if (size <= 0) {
            throw new BadRequestException("Size number cannot be less than or equal to zero.");
        }
    }
}
