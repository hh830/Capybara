package com.codingrecipe.member.service;

import com.codingrecipe.member.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class PasswordEncryptionService {

    @Autowired
    private PatientRepository userRepository;

    @Transactional
    public void encryptExistingPasswords() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userRepository.findAll().forEach(user -> {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            userRepository.save(user);
        });
    }
}
