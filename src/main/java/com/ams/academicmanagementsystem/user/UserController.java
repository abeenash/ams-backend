package com.ams.academicmanagementsystem.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody RegisterRequest request) {
        return userService.register(request.email(), request.password(), request.fullName(), request.role(), request.sectionId());
    }

    public record RegisterRequest(String email, String password, String fullName, Role role, Long sectionId) {
    }
}
