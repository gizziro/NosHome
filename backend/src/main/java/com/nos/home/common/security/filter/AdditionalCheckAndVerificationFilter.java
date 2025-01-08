package com.nos.home.common.security.filter;

import com.nos.home.common.security.details.AccountDto;
import com.nos.home.entity.account.AccountEntity;
import com.nos.home.module.account.repository.AccountRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdditionalCheckAndVerificationFilter extends OncePerRequestFilter {

    private final AccountRepository     accountRepository;
    private final RequestCache          requestCache = new HttpSessionRequestCache();
    private final static List<String>   EXCLUDED_URLS = Arrays.asList(
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
            "/waitAdminMfaCheck",   // 관리자 OTP 인증 대기 페이지
            "/verifyAdminMfa",      // 관리자 OTP 인증 확인
            "/resendMfa",           // SMS 인증 재전송
            "/resendAdminMfa"       // 관리자 OTP 인증 재전송
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

        //--------------------------------------------------------------------------------------------------------------
        // 인증된 사용자가 존재하고, 인증된 상태라면
        //--------------------------------------------------------------------------------------------------------------
        if (authentication != null && authentication.isAuthenticated())
        {
            AccountDto accountDto = (AccountDto)authentication.getPrincipal();

            //----------------------------------------------------------------------------------------------------------
            // 이메일 인증이 되어있지 않다면
            //----------------------------------------------------------------------------------------------------------
            if (!accountDto.isEmailVerified())
            {
                //------------------------------------------------------------------------------------------------------
                // 사용자 정보 조회
                //------------------------------------------------------------------------------------------------------
                Optional<AccountEntity> accountEntity = accountRepository.findByUserId(accountDto.getUserId());

                if(accountEntity.isPresent())
                {
                    //--------------------------------------------------------------------------------------------------
                    // 다시 한번 DB에서 조회하여 이메일 인증 여부를 확인 (최초 회원가입 이후, 이메일 인증을 수행하였을 경우 대비)
                    //--------------------------------------------------------------------------------------------------
                    if(!accountEntity.get().isEmailVerified())
                    {
                        response.sendRedirect("/waitEmailCheck");
                        return;
                    }
                    else {
                        accountDto.setEmailVerified(true);
                    }
                }
            }

            //----------------------------------------------------------------------------------------------------------
            // MFA 인증 여부 확인
            //----------------------------------------------------------------------------------------------------------
            String      path            = request.getRequestURI();;
            HttpSession session         = request.getSession();
            Boolean     isMfaVerified   = (Boolean) session.getAttribute("MFA_VERIFIED");

            //==========================================================================================================
            // [Step.02] 로그인 시 MFA 필수 여부 체크
            // 1. MFA 사용 여부가 true 일 경우
            // 2. MFA 인증 여부가 false 일 경우
            //==========================================================================================================
            if (accountDto.isUseMfa() && !accountDto.isMfaOtpVerified())
            {
                //------------------------------------------------------------------------------------------------------
                // 현재 요청을 저장
                //------------------------------------------------------------------------------------------------------
                requestCache.saveRequest(request, response);
                response.sendRedirect("/waitMfaCheck");
                return;
            }

            //==========================================================================================================
            // [Step.03] 관리자 페이지에서 MFA 필수 여부 체크
            // 1. 관리자 페이지 접근시
            // 2. 관리자 OTP 인증 여부가 false 일 경우
            //==========================================================================================================
//            if( path.startsWith("/admin/") && !accountDto.isAdminOtpVerified())
//            {
//                //------------------------------------------------------------------------------------------------------
//                // 현재 요청을 저장
//                //------------------------------------------------------------------------------------------------------
//                requestCache.saveRequest(request, response);
//                response.sendRedirect("/waitAdminMfaCheck");
//                return;
//            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean needsMfaVerification(AccountDto account, String path) {
        // MFA가 활성화되어 있거나 관리자 페이지 접근시
        return account.isUseMfa() || path.startsWith("/admin/");
    }
}
