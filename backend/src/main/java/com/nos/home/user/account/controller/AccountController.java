package com.nos.home.user.account.controller;

import com.nos.home.common.response.ApiResponse;
import com.nos.home.common.response.Result;
import com.nos.home.common.response.code.CommonErrorCode;
import com.nos.home.service.account.AccountService;
import com.nos.home.user.account.dto.SignUpFormDto;
import com.nos.home.user.account.session.SignUpCheckSession;
import com.nos.home.user.account.validator.SignUpFormValidator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Random;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AccountController {
    private final SignUpFormValidator   signUpFormParamsValidator;
    private final AccountService        accountService;

    @InitBinder("signUpForm")
    private void initBinder(WebDataBinder dataBinder) {
        log.info("initBinder");
        dataBinder.addValidators(signUpFormParamsValidator);
    }

    @GetMapping("/")
    public String index() {
        return "user/index";
    }

    @GetMapping("/me")
    @ResponseBody
    public Principal home(Principal principal) {
        return principal;
    }

    @GetMapping("/signin")
    public String login() {
        return "user/account/login";
    }

    //------------------------------------------------------------------------------------------------------------------
    // 사용자 회원 가입
    //------------------------------------------------------------------------------------------------------------------
    @GetMapping("/signup")
    public String moveToSignUpPage(Model model, HttpSession session)
    {
        SignUpFormDto param = new SignUpFormDto();

        //--------------------------------------------------------------------------------------------------------------
        // 회원 가입을 위한 Transaction ID 값을 확인 한다.
        //--------------------------------------------------------------------------------------------------------------
        if(session.getAttribute("SignUpCheckSession") == null)
        {
            // UUID를 문자열로 변환
            SignUpCheckSession joinCheckSession = new SignUpCheckSession();
            log.info("회원 가입을 위한 Transaction ID 생성 : {}", joinCheckSession.getUuid());
            session.setAttribute("SignUpCheckSession", joinCheckSession);
        }
        else {
            SignUpCheckSession joinCheckSession = (SignUpCheckSession)session.getAttribute("SignUpCheckSession");
            log.info("Transaction ID : {}", joinCheckSession.getUuid());
        }

        model.addAttribute("signUpForm", param);
        log.info("회원 가입 페이지 요청");

        return "user/account/signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute("signUpForm") SignUpFormDto signUpFormDto,
                         HttpSession session,
                         BindingResult bindingResult,
                         Model model)
    {
        log.info("회원 가입 요청 (Post)");
        System.out.println("signUpFormParams = " + signUpFormDto);

        //--------------------------------------------------------------------------------------------------------------
        //
        //--------------------------------------------------------------------------------------------------------------
        if(bindingResult.hasErrors())
        {
            log.info("에러가 있습니다. : 에러 개수 : {}", bindingResult.getErrorCount());
            bindingResult.getFieldErrors().forEach(error -> log.info("필드: {} 에러 메시지: {}", error.getField(), error.getDefaultMessage()));
            model.addAttribute("signUpForm", signUpFormDto);
            /// model.addAttribute("org.springframework.validation.BindingResult.signUpForm", bindingResult);
            return "user/account/signup";
        }
        // 회원가입 시도
    //        accountService.createUser(signUpFormParams);

        if(session.getAttribute("SignUpCheckSession") != null)
        {
            SignUpCheckSession joinCheckSession = (SignUpCheckSession)session.getAttribute("SignUpCheckSession");
            log.info("Transaction ID : {}", joinCheckSession.getUuid());

            if(joinCheckSession.isOtpCheck())
            {
                log.info("회원 가입을 시도합니다.");
                // accountService.createUser(signUpFormDto);
            }
            else
            {
                log.info("OTP 확인이 되지 않았습니다.");
                return "redirect:/signup";
            }
        }
        else
        {
            return "redirect:/signup";
        }

        return "redirect:/signupComplete";
    }

    @GetMapping("/signupComplete")
    public String signUpComplete()
    {
        log.info("회원 가입 완료");
        return "user/account/signupComplete";
    }

    @PostMapping("/checkId")
    @ResponseBody
    public ApiResponse<String> checkId(@RequestParam("userId") String userId)
    {
        log.info("아이디 중복 체크 요청 : {}", userId);

        return ApiResponse.OK("사용 가능한 아이디 입니다.");
    }

    @PostMapping("/sendOtp")
    @ResponseBody
    public ApiResponse<String> sendOtp(@RequestParam("phone") String phone, HttpSession session)
    {
        log.info("OTP 전송 요청 : {}", phone);

        //--------------------------------------------------------------------------------------------------------------
        // 회원 가입을 위한 Transaction ID 값을 확인 한다.
        //--------------------------------------------------------------------------------------------------------------
        if(session.getAttribute("SignUpCheckSession") != null)
        {
            Random random = new Random();
            // 100000부터 999999 사이의 랜덤 숫자 생성
            int sixDigitNumber = 100000 + random.nextInt(900000);
            log.info("OTP : {}", sixDigitNumber);

            SignUpCheckSession joinCheckSession = (SignUpCheckSession)session.getAttribute("SignUpCheckSession");
            log.info("Transaction ID : {}", joinCheckSession.getUuid());
            joinCheckSession.setOtpNumber(String.valueOf(sixDigitNumber));
        }
        else
        {
            return ApiResponse.ERROR(CommonErrorCode.INVALID_STATE);
        }

        return ApiResponse.OK("OTP 전송 완료");
    }

    @PostMapping("/verifyOtp")
    @ResponseBody
    public ApiResponse<String> verifyOtp(@RequestParam("otp") String otp, HttpSession session)
    {
        log.info("OTP 값 : {}", otp);

        //--------------------------------------------------------------------------------------------------------------
        // 회원 가입을 위한 Transaction ID 값을 확인 한다.
        //--------------------------------------------------------------------------------------------------------------
        if(session.getAttribute("SignUpCheckSession") != null)
        {
            SignUpCheckSession joinCheckSession = (SignUpCheckSession)session.getAttribute("SignUpCheckSession");
            log.info("Transaction ID : {}", joinCheckSession.getUuid());

            if(joinCheckSession.getOtpNumber().equals(otp))
            {
                log.info("OTP 값이 일치합니다. : {}",otp);
                joinCheckSession.setOtpCheck(true);
                return ApiResponse.OK("OTP 확인 완료");
            }
            else
            {
                log.info("OTP 값이 일치하지 않습니다. : {}",otp);
                return ApiResponse.ERROR(CommonErrorCode.NOT_ACCEPTED);
            }
        }
        else
        {
            return ApiResponse.ERROR(CommonErrorCode.INVALID_STATE);
        }
    }
}
