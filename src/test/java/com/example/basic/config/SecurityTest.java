package com.example.basic.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Slf4j
class SecurityTests {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    void passwordCreate() {
        String encPwd = passwordEncoder.encode("123");
        log.info("encPwd {}", encPwd);
    }
}

