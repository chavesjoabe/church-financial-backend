package com.treasury.treasury.user.dto;

import com.treasury.treasury.user.constants.UserRoles;

public record GetUserResponseDto(
    String createdAt,
    String updatedAt,
    String name,
    String email,
    String document,
    String id,
    Boolean isActive,
    UserRoles role) {
}
