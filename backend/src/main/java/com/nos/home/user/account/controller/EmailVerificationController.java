package com.nos.home.user.account.controller;

import com.nos.home.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class EmailVerificationController {

    @GetMapping("/waitEmailCheck")
    public String waitEmailCheck(Model model) {
//        if (account == null) {
//            return "redirect:/signin";
//        }
//
//        if (account.isEmailVerified()) {
//            return "redirect:/";
//        }
//
//        model.addAttribute("email", account.getEmail());
//        return "user/account/wait-email-check";
        return "user/account/waitEmailCheck";
    }

    @PostMapping("/resendEmailVerification")
    @ResponseBody
    public ApiResponse<?> resendVerification() {
        // 이메일 재발송 로직
        return ApiResponse.OK();
    }
}
