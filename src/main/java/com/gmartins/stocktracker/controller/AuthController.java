package com.gmartins.stocktracker.controller;

import com.gmartins.stocktracker.controller.request.AuthRequest;
import com.gmartins.stocktracker.controller.response.AuthResponse;
import com.gmartins.stocktracker.entity.User;
import com.gmartins.stocktracker.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stock/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        UsernamePasswordAuthenticationToken userAndPass =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        User user = (User) authentication.getPrincipal();

        String token = tokenService.generateToken(user);

        return ResponseEntity.ok(AuthResponse
                        .builder()
                        .accessToken(token)
                        .name(user.getName())
                        .build());
    }
}
