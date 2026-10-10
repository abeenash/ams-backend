package com.ams.academicmanagementsystem.security;

import com.ams.academicmanagementsystem.user.AppUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {
    public AppUserDetails getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (AppUserDetails) authentication.getPrincipal();
    }
}
