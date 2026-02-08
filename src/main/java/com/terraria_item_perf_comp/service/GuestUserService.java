package com.terraria_item_perf_comp.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.terraria_item_perf_comp.models.User;
import com.terraria_item_perf_comp.models.UserRole;
import com.terraria_item_perf_comp.repository.UserRepository;
import com.terraria_item_perf_comp.repository.UserRoleRepository;

@Service
public class GuestUserService {

    private static final int GUEST_USER_ROLE_ID = 1; // USER 역할

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public GuestUserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Transactional
    public User getOrCreateGuestUser(String ipAddress) {
        String ipHash = hashIpAddress(ipAddress);
        Optional<User> existingUser = userRepository.findByIpHash(ipHash);

        if (existingUser.isPresent()) {
            return existingUser.get();
        }

        // 새 Guest User 생성 (role_id = 1은 USER 역할)
        UserRole guestRole = userRoleRepository.findById(GUEST_USER_ROLE_ID)
                .orElseThrow(() -> new RuntimeException("Guest user role not found"));

        User newGuestUser = User.builder()
                .ipHash(ipHash)
                .role(guestRole)
                .build();

        return userRepository.save(newGuestUser);
    }

    private String hashIpAddress(String ipAddress) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(ipAddress.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}

