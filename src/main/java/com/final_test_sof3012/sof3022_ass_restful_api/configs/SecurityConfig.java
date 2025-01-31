package com.final_test_sof3012.sof3022_ass_restful_api.configs;

import com.final_test_sof3012.sof3022_ass_restful_api.Repositories.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class SecurityConfig {
     AccountRepository accountRepository;

     @Bean
    public AuthenticationManager authenticationManager(){
         DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
         provider.setUserDetailsService(username -> (org.springframework.security.core.userdetails.UserDetails) accountRepository.findById(username)
                 .orElseThrow(() -> new UsernameNotFoundException("User not found")));
         provider.setPasswordEncoder(passwordEncoder());
         return new ProviderManager(provider);
     }

     @Bean
     public PasswordEncoder passwordEncoder(){
         return new BCryptPasswordEncoder();
     }
}
