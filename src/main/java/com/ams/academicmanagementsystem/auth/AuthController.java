package com.ams.academicmanagementsystem.auth;

import com.ams.academicmanagementsystem.security.JwtService;
import com.ams.academicmanagementsystem.user.AppUserDetails;
import com.ams.academicmanagementsystem.user.Role;
import com.ams.academicmanagementsystem.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        String token = jwtService.generateToken(userDetails.getUsername(), userDetails.getUser().getRole().name());
        return new LoginResponse(token, user.getRole(), user.getFullName());
    }


    public record LoginRequest(String email, String password) {
    }

    public record LoginResponse(String token, Role role, String fullName) {
    }
}
