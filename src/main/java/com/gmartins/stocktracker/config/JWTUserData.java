package com.gmartins.stocktracker.config;

import com.gmartins.stocktracker.entity.enums.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class JWTUserData {
    private String userId;
    private String email;
    private List<Role> roles;

}
