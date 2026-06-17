package com.project.demo;

import com.project.demo.logic.entity.rol.Rol;
import com.project.demo.logic.entity.rol.RolRepository;
import com.project.demo.logic.entity.user.User;
import com.project.demo.logic.entity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (rolRepository.count() == 0) {
            Rol adminRol = new Rol();
            adminRol.setName("SUPER-ADMIN-ROLE");
            rolRepository.save(adminRol);

            Rol userRol = new Rol();
            userRol.setName("USER");
            rolRepository.save(userRol);
        }

        if (userRepository.count() == 0) {
            Rol adminRol = rolRepository.findAll()
                    .stream()
                    .filter(r -> r.getName().equals("SUPER-ADMIN-ROLE"))
                    .findFirst().orElseThrow();

            Rol userRol = rolRepository.findAll()
                    .stream()
                    .filter(r -> r.getName().equals("USER"))
                    .findFirst().orElseThrow();

            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@demo.com");
            admin.setPassword(passwordEncoder.encode("Admin123"));
            admin.setRol(adminRol);
            userRepository.save(admin);

            User user = new User();
            user.setName("User");
            user.setEmail("user@demo.com");
            user.setPassword(passwordEncoder.encode("User123"));
            user.setRol(userRol);
            userRepository.save(user);
        }
    }
}