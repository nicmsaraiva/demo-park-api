package com.nicmsaraiva.demoparkapi.service;

import com.nicmsaraiva.demoparkapi.entity.User;
import com.nicmsaraiva.demoparkapi.exception.EntityNotFoundException;
import com.nicmsaraiva.demoparkapi.exception.PasswordInvalidException;
import com.nicmsaraiva.demoparkapi.exception.UsernameUniqueViolationException;
import com.nicmsaraiva.demoparkapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        try {
            return userRepository.save(user);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(String.format("Username '%s' already exists", user.getUsername()));
        }

    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User '%s' not exists.", id))
        );
    }

    @Transactional
    public User updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordInvalidException("New password does not match password confirmation.");
        }
        User user = findById(id);
        if (!user.getPassword().equals(currentPassword)) {
            throw new PasswordInvalidException("Your password does not match.");
        }
        user.setPassword(newPassword);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
