package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.common.responses.PaginatedResponse;
import com.faithjoyfundation.autopilotapi.v1.common.utils.RandomPasswordGenerator;
import com.faithjoyfundation.autopilotapi.v1.common.utils.TempPasswordEmailMessage;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.user.RoleRequest;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.user.UserDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.user.UserListDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.user.UserRequest;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.BadRequestException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ConflictException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ForbiddenException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.auth.Role;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.auth.User;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.RoleRepository;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.UserRepository;
import com.faithjoyfundation.autopilotapi.v1.services.EmailService;
import com.faithjoyfundation.autopilotapi.v1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
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
        List<Role> roles = getRoles(userRequest.getRoles().stream().map(RoleRequest::getId).toList());
        validateUniqueEmail(userRequest.getEmail(), null);

        String tempPassword = RandomPasswordGenerator.generate(8);
        emailService.sendEmail(userRequest.getEmail(), "AutoPilot: Temporal Password", TempPasswordEmailMessage.generate(tempPassword));

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setRoles(new HashSet<>(roles));
        user.setPassword(passwordEncoder.encode(tempPassword));
        return new UserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO update(Long id, UserRequest userRequest, Principal principal) {
        User user = findModelById(id);
        User currentUser = getCurrentUser(principal);
        List<Role> roles = getRoles(userRequest.getRoles().stream().map(RoleRequest::getId).toList());

        if (user.getId().equals(1L)) {
            throw new BadRequestException("You cannot update the default admin user.");
        }
        if(currentUser.getId().equals(user.getId()) && !roles.stream().map(Role::getName).toList().contains("ROLE_ADMIN")) {
            throw new BadRequestException("You cannot remove your admin role.");
        }
        validateUniqueEmail(userRequest.getEmail(), id);

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setRoles(new HashSet<>(roles));
        return new UserDTO(userRepository.save(user));
    }

    @Override
    public boolean resetTempPassword(Long id) {
        User user = findModelById(id);

        if (user.getId().equals(1L)) {
            throw new ForbiddenException("You cannot reset the default admin user's password.");
        }
        String tempPassword = RandomPasswordGenerator.generate(8);
        emailService.sendEmail(user.getEmail(), "AutoPilot: Temporal Password", TempPasswordEmailMessage.generate(tempPassword));
        user.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean delete(Long id, Principal principal) {
        User user = findModelById(id);
        User currentUser = getCurrentUser(principal);
        if(user.getId().equals(1L)) {
            throw new ConflictException("You cannot delete the default admin user.");
        }
        if (currentUser != null && currentUser.getId().equals(user.getId())) {
            throw new BadRequestException("You cannot delete yourself.");
        }
        userRepository.delete(user);
        return true;
    }

    private User getCurrentUser(Principal principal) {
        return userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + principal.getName()));
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
