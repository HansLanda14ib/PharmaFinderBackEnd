package com.localisation.services;

import com.localisation.entities.Pharmacy;
import com.localisation.entities.Role;
import com.localisation.entities.User;
import com.localisation.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    private final UserRepository userRepository;

    public PermissionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean isAdmin(User user) {
        return user != null && user.getRole() == Role.ADMIN;
    }

    public boolean isOwner(User user, Pharmacy pharmacy) {
        return user != null && user.getRole() == Role.OWNER && user.getEmail().equals(pharmacy.getUser().getEmail());
    }

    public boolean isFirstOwner(User user) {
        return user != null && user.getRole() == Role.OWNER;
    }

    public boolean isUser(User user) {
        return user != null && user.getRole() == Role.USER;
    }
}
