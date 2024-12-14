package com.nos.home.common.security.filter;

import com.nos.home.common.security.details.AccountContext;
import com.nos.home.common.security.details.AccountDto;
import com.nos.home.entity.account.AccountEntity;
import com.nos.home.repository.account.AccountRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailVerificationFilter extends OncePerRequestFilter {

    private final AccountRepository accountRepository;

    private final static List<String> EXCLUDED_URLS = Arrays.asList(
            "/waitEmailCheck",
            "/verifyEmail",
            "/resendEmailVerification",
            "/css/",
            "/js/",
            "/logout",
            "/favicon"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return EXCLUDED_URLS.stream().anyMatch(path::startsWith);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            AccountDto accountDto = (AccountDto)authentication.getPrincipal();

            if (!accountDto.isEmailVerified()) {
                AccountEntity accountEntity = accountRepository.findByUserId(accountDto.getUserId());

                if(!accountEntity.isEmailVerified()) {
                    response.sendRedirect("/waitEmailCheck");
                    return;
                }
                else {
                    accountDto.setEmailVerified(true);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
