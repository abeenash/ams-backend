package com.ams.academicmanagementsystem.user;

public record UserResponse(Long id, String email, String fullname, Role role) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getFullName(), user.getRole());
    }
}
