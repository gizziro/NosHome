package com.nos.home.user.account.validator;

import com.nos.home.entity.account.AccountEntity;
import com.nos.home.repository.account.AccountRepository;
import com.nos.home.user.account.dto.SignUpFormDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class SignUpFormValidator  implements Validator {
    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpFormDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpFormDto form = (SignUpFormDto) target;
        log.info("SignUpFormDto : {}", form);

        AccountEntity accountEntity = accountRepository.findByUserId(form.getUserId());

        // 사용자 이름 중복 체크
        log.info("사용자 이름 중복 체크");
        if (accountEntity != null) {
            errors.rejectValue("userId", "userId.exists", "이미 사용중인 ID입니다.");
        }

        // TODO : 이메일 중복 체크

        // 비밀번호 확인 일치 여부 확인
        log.info("비밀번호 확인 일치 여부 확인");
        if (!form.getPassword().equals(form.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm", "passwordConfirm.mismatch", "비밀번호가 일치하지 않습니다.");
        }
    }
}