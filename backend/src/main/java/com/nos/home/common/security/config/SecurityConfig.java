package com.nos.home.common.security.config;

import com.nos.home.common.security.filter.EmailVerificationFilter;
import com.nos.home.common.security.provider.NosUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig
{
//    private final UserDetailsService userDetailsService;
    private final AuthenticationProvider authenticationProvider;
    private final EmailVerificationFilter emailVerificationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/favicon.*","/*/icon-*").permitAll() // 정적 자원 설정
                        .requestMatchers("/","/signup","/signin", "/signupComplete", "/sendOtp", "/verifyOtp", "/checkId",
                                "/waitEmailCheck", "/verifyEmail", "/resendEmailVerification").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/signin")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/logout").permitAll())
                .authenticationProvider(authenticationProvider)
                .addFilterAfter(emailVerificationFilter, SecurityContextPersistenceFilter.class)
        ;
        return http.build();
    }

}
