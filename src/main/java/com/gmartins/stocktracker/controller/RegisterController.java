package com.gmartins.stocktracker.controller;

import com.gmartins.stocktracker.controller.request.RegisterUserRequest;
import com.gmartins.stocktracker.entity.User;
import com.gmartins.stocktracker.entity.enums.Role;
import com.gmartins.stocktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock/register")
@RequiredArgsConstructor
public class RegisterController {

    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Void> register(@RequestHeader(value = "isAdmin", required = false) boolean isAdmin, @RequestBody RegisterUserRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .roles(isAdmin ? List.of(Role.ADMIN, Role.USER) : List.of(Role.USER))
                .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
