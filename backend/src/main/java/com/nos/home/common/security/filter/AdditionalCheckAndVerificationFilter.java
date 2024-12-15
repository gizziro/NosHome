package com.nos.home.common.security.filter;

import com.nos.home.common.security.details.AccountDto;
import com.nos.home.entity.account.AccountEntity;
import com.nos.home.repository.account.AccountRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdditionalCheckAndVerificationFilter extends OncePerRequestFilter {

    private final AccountRepository accountRepository;

    private final static List<String> EXCLUDED_URLS = Arrays.asList(
            "/waitEmailCheck",
            "/verifyEmail",
            "/resendEmailVerification",
            "/css/",
            "/js/",
            "/logout",
            "/favicon",
            //----------------------------------------------------------------------------------------------------------
            // SMS 2차 인증 관련
            //----------------------------------------------------------------------------------------------------------
            "/waitMfaCheck",        // SMS 인증 대기 페이지
            "/verifyMfa",           // SMS 인증 확인
            "/resendMfa"            // SMS 인증 재전송
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

        // SecurityContextHolder에 저장된 Authentication 객체를 가져온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증된 사용자가 존재하고, 인증된 상태라면
        if (authentication != null && authentication.isAuthenticated())
        {
            AccountDto accountDto = (AccountDto)authentication.getPrincipal();

            // 이메일 인증이 되어있지 않다면
            if (!accountDto.isEmailVerified())
            {

                AccountEntity accountEntity = accountRepository.findByUserId(accountDto.getUserId());

                if(!accountEntity.isEmailVerified()) {
                    response.sendRedirect("/waitEmailCheck");
                    return;
                }
                else {
                    accountDto.setEmailVerified(true);
                }
            }

            String      path            = request.getRequestURI();
            HttpSession session         = request.getSession();
            Boolean     isMfaVerified   = (Boolean) session.getAttribute("MFA_VERIFIED");

            // MFA가 필요한 경우 체크
            if (needsMfaVerification(accountDto, path) && !Boolean.TRUE.equals(isMfaVerified))
            {
                response.sendRedirect("/waitMfaCheck");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean needsMfaVerification(AccountDto account, String path) {
        // MFA가 활성화되어 있거나 관리자 페이지 접근시
        return account.isUseMfa() || path.startsWith("/admin/");
    }
}
