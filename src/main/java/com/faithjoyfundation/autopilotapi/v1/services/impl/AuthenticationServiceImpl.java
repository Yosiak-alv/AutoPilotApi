package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.config.security.jwt.JwtTokenProvider;
import com.faithjoyfundation.autopilotapi.v1.dto.AuthResponseDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.requests.LoginRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.requests.RegisterRequest;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.BadRequestException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.Branch;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.auth.Role;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.auth.User;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.BranchRepository;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.RoleRepository;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.UserRepository;
import com.faithjoyfundation.autopilotapi.v1.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponseDTO login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        ));

        /* 02 - SecurityContextHolder is used to allows the rest of the application to know
        that the user is authenticated and can use user data from Authentication object */
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 03 - Generate the token based on username and secret key
        String token = jwtTokenProvider.generateToken(authentication);

        return new AuthResponseDTO("You are logged in successfully", request.getEmail(), token);
    }

    public Map<Object,Object> register(RegisterRequest request) {
        Branch branch = branchRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Branch not found"));
        Role role = roleRepository.findByName("ROLE_EMPLOYEE").orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setBranch(branch);
        user.setRoles(Collections.singleton(role));

        User newUser = userRepository.save(user);

        Map<Object,Object> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("user", newUser);

        return response;

    }

}
