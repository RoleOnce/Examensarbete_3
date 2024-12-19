package org.roleonce.examensarbete_3.config;

import jakarta.annotation.PostConstruct;
import org.roleonce.examensarbete_3.authorities.UserRole;
import org.roleonce.examensarbete_3.dao.UserDAO;
import org.roleonce.examensarbete_3.model.CustomUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAdminInitializer {

    private final PasswordEncoder passwordEncoder;
    private final UserDAO userDAO;

    public CustomAdminInitializer(PasswordEncoder passwordEncoder, UserDAO userDAO) {
        this.passwordEncoder = passwordEncoder;
        this.userDAO = userDAO;
    }
    @Value("${DEFAULT_ADMIN_PASSWORD}")
    private String adminPassword;

    @PostConstruct
    public void ensureAdminUserExists() {


        String defaultAdminName = "admin";

        if (userDAO.findByUsername(defaultAdminName).isEmpty()) {
            CustomUser adminUser = new CustomUser(
                    "Admin",
                    "Admin",
                    defaultAdminName,
                    passwordEncoder.encode(adminPassword),
                    "admin@gmail.com",
                    UserRole.ADMIN,
                    true,
                    true,
                    true,
                    true
            );
            userDAO.save(adminUser);
            System.out.println("Default admin user created");
        }
    }
}
