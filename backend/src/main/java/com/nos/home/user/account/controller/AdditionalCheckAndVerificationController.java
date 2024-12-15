package com.nos.home.user.account.controller;

import com.nos.home.common.response.ApiResponse;
import com.nos.home.common.response.Result;
import com.nos.home.common.response.code.CommonErrorCode;
import com.nos.home.common.security.CurrentUser;
import com.nos.home.common.security.details.AccountDto;
import com.nos.home.common.sms.SmsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Random;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdditionalCheckAndVerificationController {
    private final SmsService smsService;
    private final RequestCache requestCache = new HttpSessionRequestCache();


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

    //==================================================================================================================
    // SMS 2차 인증 관련
    //==================================================================================================================\
    @GetMapping("/waitMfaCheck")
    public String waitMfaCheck(@CurrentUser AccountDto account, HttpServletRequest request, Model model)
    {
        //--------------------------------------------------------------------------------------------------------------
        // 로그인이 되어있지 않다면 로그인 페이지로 이동
        //--------------------------------------------------------------------------------------------------------------
        if (account == null)
        {
            log.info("로그인이 되어있지 않습니다.");
            return "redirect:/signin";
        }
        //--------------------------------------------------------------------------------------------------------------
        // 2차 인증 번호 확인
        //--------------------------------------------------------------------------------------------------------------
        if(account.GenerateMfaOtpNumber())
        {
            log.info("OTP 번호 신규 전송 : {}", account.getMfaOtpNumber());
            smsService.sendOne(account.getPhone(), String.format("NOS 인증번호는 [%s] 입니다.", account.getMfaOtpNumber()));
        }
        //--------------------------------------------------------------------------------------------------------------
        model.addAttribute("phone", account.getPhone());
        return "user/account/waitSmsCheck";
    }


    @PostMapping("/verifyMfa")
    public String verifyMfa(@CurrentUser AccountDto account, @RequestParam String otpCode,
                            HttpServletRequest request, HttpServletResponse response, HttpSession session)
    {
        //--------------------------------------------------------------------------------------------------------------
        // OTP 인증 확인
        //--------------------------------------------------------------------------------------------------------------
        log.info("입력한 OTP 코드 : {}", otpCode);

        if(account.getMfaOtpNumber().equals(otpCode))
        {
            log.info("OTP 인증 성공");
            account.setMfaOtpVerified(true);

            //--------------------------------------------------------------------------------------------------------------
            // 저장된 요청 확인
            //--------------------------------------------------------------------------------------------------------------
            SavedRequest        savedRequest    = requestCache.getRequest(request, response);
            String              redirectUrl     = savedRequest != null ? savedRequest.getRedirectUrl() : "/";
            log.info("redirectUrl : {}", redirectUrl);
            // 저장된 요청 제거
            requestCache.removeRequest(request, response);
            // redirect: 접두사를 붙여 리다이렉트
            return "redirect:" + redirectUrl;
        }
        else
        {
            log.info("OTP 인증 실패");
            return "redirect:/waitMfaCheck";
        }
    }


    @GetMapping("/waitAdminMfaCheck")
    public String waitAdminMfaCheck(@CurrentUser AccountDto account, HttpServletRequest request, Model model)
    {
        //--------------------------------------------------------------------------------------------------------------
        // 로그인이 되어있지 않다면 로그인 페이지로 이동
        //--------------------------------------------------------------------------------------------------------------
        if (account == null)
        {
            log.info("로그인이 되어있지 않습니다.");
            return "redirect:/signin";
        }
        //--------------------------------------------------------------------------------------------------------------
        // 2차 인증 번호 확인
        //--------------------------------------------------------------------------------------------------------------
        if(account.GenerateAdminOtpNumber())
        {
            log.info("OTP 번호 신규 전송 : {}", account.getAdminOtpNumber());
            smsService.sendOne(account.getPhone(), String.format("NOS 인증번호는 [%s] 입니다.", account.getAdminOtpNumber()));
        }
        //--------------------------------------------------------------------------------------------------------------
        model.addAttribute("phone", account.getPhone());
        return "admin/account/waitAdminSmsCheck";
    }

    @PostMapping("/verifyAdminMfa")
    public String verifyAdminMfa(@CurrentUser AccountDto account, @RequestParam String otpCode,
                            HttpServletRequest request, HttpServletResponse response, HttpSession session)
    {
        //--------------------------------------------------------------------------------------------------------------
        // OTP 인증 확인
        //--------------------------------------------------------------------------------------------------------------
        log.info("입력한 OTP 코드 : {}", otpCode);

        if(account.getAdminOtpNumber().equals(otpCode))
        {
            log.info("OTP 인증 성공");
            account.setAdminOtpVerified(true);
            //----------------------------------------------------------------------------------------------------------
            // 저장된 요청 확인
            //----------------------------------------------------------------------------------------------------------
            SavedRequest        savedRequest    = requestCache.getRequest(request, response);
            String              redirectUrl     = savedRequest != null ? savedRequest.getRedirectUrl() : "/";
            log.info("redirectUrl : {}", redirectUrl);
            //----------------------------------------------------------------------------------------------------------
            // 저장된 요청 제거
            //----------------------------------------------------------------------------------------------------------
            requestCache.removeRequest(request, response);
            // redirect: 접두사를 붙여 리다이렉트
            return "redirect:" + redirectUrl;
        }
        else
        {
            log.info("OTP 인증 실패");
            return "redirect:/waitAdminMfaCheck";
        }
    }

    @PostMapping("/resendMfa")
    @ResponseBody
    public ApiResponse<?> resendMfa(@CurrentUser AccountDto account) {
        return ApiResponse.OK();
    }
}
