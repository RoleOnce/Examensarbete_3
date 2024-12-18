package org.roleonce.examensarbete_3.config.security;

import org.roleonce.examensarbete_3.config.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/register", "/login", "/advertisement/**").permitAll()
                        .requestMatchers("/logout", "/upload").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/delete-listing", "/delete-user").hasRole("ADMIN")
                        .requestMatchers("/output.css").permitAll()
                        //.requestMatchers("/css/**", "/js/**", "/images/**", "/static/**", "/output.css").permitAll()
                        .anyRequest().authenticated())

                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .loginPage("/login")
                )

                // Tar med säkerhet bort cookies, autentiseringsinfo och
                // sessionen när användaren loggar ut
                .logout(logoutConfigurer -> logoutConfigurer
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("remember-me", "JSESSIONID")
                        .logoutUrl("/custom-logout") // Används i logout.html som >>th:action="@{/custom-logout}"<<
                )

                .rememberMe(rememberMeConfigurer -> rememberMeConfigurer
                        .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                        .key("someSecureKey")
                        .userDetailsService(customUserDetailsService)
                        .rememberMeParameter("remember-me")
                );

        return http.build();
    }

}
