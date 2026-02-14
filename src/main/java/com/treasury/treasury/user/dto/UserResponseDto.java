package com.treasury.treasury.user.dto;

import com.treasury.treasury.user.constants.UserRoles;

public record UserResponseDto(
    String id,
    String name,
    String document,
    Boolean isActive,
    String email,
    UserRoles role) {
}
