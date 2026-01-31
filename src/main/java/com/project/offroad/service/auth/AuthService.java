package com.project.offroad.service.auth;


import com.project.offroad.domain.entity.User;
import com.project.offroad.domain.enumtype.Role;
import com.project.offroad.exception.BusinessException;
import com.project.offroad.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(String fullName, String email, String rawPassword) {

        if (userRepository.existsByEmail(email)) {
            throw new BusinessException("Email already registered");
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(Role.USER);
        user.setActive(true);

        return userRepository.save(user);
    }
}