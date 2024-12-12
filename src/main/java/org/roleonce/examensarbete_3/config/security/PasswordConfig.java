package org.roleonce.examensarbete_3.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Denna klass är en Spring-konfigurationsklass som definierar en PasswordEncoder-böna
// som används för att kryptera och verifiera lösenord i applikationen >>>UserController<<<.
@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(15);
    }

}
