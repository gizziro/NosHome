package com.nos.home.common.security.listener;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthenticationEventListener {

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        // Handle successful authentication, e.g. logging or auditing
        log.debug("인증 성공 : " + success.getAuthentication().getName());
        String username = success.getAuthentication().getName();
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failure) {
        // Handle unsuccessful authentication, e.g. logging or auditing
        log.debug("인증 실패: " + failure.getException().getMessage());
        String username = failure.getAuthentication().getName();
    }
}
