package com.nos.home.user.account.controller;

import com.nos.home.common.response.ApiResponse;
import com.nos.home.common.response.Result;
import com.nos.home.common.response.code.CommonErrorCode;
import com.nos.home.common.security.CurrentUser;
import com.nos.home.common.security.details.AccountDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class AdditionalCheckAndVerificationController {

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

    //------------------------------------------------------------------------------------------------------------------
    // SMS 2차 인증 관련 페이지 이동
    //------------------------------------------------------------------------------------------------------------------
//    @GetMapping("/waitMfaCheck")
//    public String waitMfaCheck(@AuthenticationPrincipal Object principal, Model model) {
//        System.out.println("Principal class: " + principal.getClass());
//        System.out.println("Principal: " + principal);
//        return "user/account/waitSmsCheck";
//    }

    @GetMapping("/waitMfaCheck")
    public String waitMfaCheck(@CurrentUser AccountDto account, Model model)
    {
        //--------------------------------------------------------------------------------------------------------------
        // 로그인이 되어있지 않다면 로그인 페이지로 이동
        //--------------------------------------------------------------------------------------------------------------
        if (account == null) {
            return "redirect:/signin";
        }
        //--------------------------------------------------------------------------------------------------------------
        //
        //--------------------------------------------------------------------------------------------------------------
        model.addAttribute("phone", account.getPhone());

        return "user/account/waitSmsCheck";
    }

    @PostMapping("/verifyMfa")
    @ResponseBody
    public ApiResponse<?> verifyMfa(@CurrentUser AccountDto account, @RequestParam String code, HttpSession session) {


        return ApiResponse.ERROR(CommonErrorCode.BAD_REQUEST);
    }

    @PostMapping("/resendMfa")
    @ResponseBody
    public ApiResponse<?> resendMfa(@CurrentUser AccountDto account) {
        return ApiResponse.OK();
    }
}
