package com.kevin.todo.orderandinventorymanagementsystem.config;

import com.kevin.todo.orderandinventorymanagementsystem.entity.UserEntity;
import com.kevin.todo.orderandinventorymanagementsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AdminSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.seed.username:admin}")
    private String seedUsername;

    @Value("${admin.seed.password}")
    private String seedPassword;

    @Override
    public void run(String... args) {
        if (userRepository.findByUserName(seedUsername) != null) {
            log.info("Admin seed skipped - user '{}' already exists", seedUsername);
            return;
        }

        UserEntity admin = new UserEntity();
        admin.setUserName(seedUsername);
        admin.setPassword(passwordEncoder.encode(seedPassword)); // encode ONCE, here
        admin.setEmail("admin@system.local");
        admin.setRoles(List.of("USER", "ADMIN"));
        userRepository.save(admin);

        log.info("Seeded initial admin user '{}'", seedUsername);
    }
}
