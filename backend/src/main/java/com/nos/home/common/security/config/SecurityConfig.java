package com.nos.home.common.security.config;

import com.nos.home.common.security.filter.AdditionalCheckAndVerificationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig
{
//    private final UserDetailsService userDetailsService;
    private final AuthenticationProvider                authenticationProvider;
    private final AdditionalCheckAndVerificationFilter  additionalCheckAndVerificationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/favicon.*","/*/icon-*").permitAll() // 정적 자원 설정
                        .requestMatchers("/","/signup","/signin", "/signupComplete", "/sendOtp", "/verifyOtp", "/checkId",
                                "/waitEmailCheck", "/verifyEmail", "/resendEmailVerification",
                                "/waitSmsCheck").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/signin")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // GET 방식 허용
                        .logoutSuccessUrl("/")  // 로그아웃 성공 후 "/" 경로로 리다이렉트
                        .permitAll())
                .authenticationProvider(authenticationProvider)
                .addFilterAfter(additionalCheckAndVerificationFilter, SecurityContextPersistenceFilter.class)
        ;
        return http.build();
    }

}
