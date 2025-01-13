package com.nos.home.common.config;

import com.nos.home.common.security.details.AccountDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty(); // 익명 사용자의 경우 Optional.empty() 반환
            }

            AccountDto accountDto = (AccountDto) authentication.getPrincipal();

            return Optional.of(accountDto.getUserId());
        };
    }
}
