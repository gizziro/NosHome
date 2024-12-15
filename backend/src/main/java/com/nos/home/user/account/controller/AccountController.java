package com.nos.home.user.account.controller;

import com.nos.home.common.response.ApiResponse;
import com.nos.home.common.response.Result;
import com.nos.home.common.response.code.CommonErrorCode;
import com.nos.home.common.sms.SmsService;
import com.nos.home.service.account.AccountService;
import com.nos.home.user.account.dto.SignUpFormDto;
import com.nos.home.user.account.dto.SignUpOtpRespDto;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Random;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AccountController {
    private final SignUpFormValidator   signUpFormParamsValidator;
    private final AccountService        accountService;
    private final SmsService            smsService;

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

        // FlashAttribute에서 전달된 signUpForm이 있는지 확인
        if (!model.containsAttribute("signUpForm")) {
            log.info("새로운 SignUpFormDto 객체 생성");
            model.addAttribute("signUpForm", new SignUpFormDto());
        } else {
            log.info("기존 SignUpFormDto 데이터 유지");
        }

        return "user/account/signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute("signUpForm") SignUpFormDto signUpFormDto,
                         BindingResult bindingResult,
                         HttpSession session,
                         RedirectAttributes redirectAttributes,
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

            // bindingResult도 함께 전달
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.signUpForm", bindingResult);
            redirectAttributes.addFlashAttribute("signUpForm", signUpFormDto);
            return "redirect:/signup";
        }

        if(session.getAttribute("SignUpCheckSession") != null)
        {
            SignUpCheckSession joinCheckSession = (SignUpCheckSession)session.getAttribute("SignUpCheckSession");
            log.info("Transaction ID : {}", joinCheckSession.getUuid());

            if(joinCheckSession.isOtpCheck())
            {
                log.info("회원 가입을 시도합니다.");
                accountService.createUser(signUpFormDto);
                log.info("회원가입 처리 완료");
                joinCheckSession.clear();
            }
            else
            {
                log.info("OTP 확인이 되지 않았습니다.");
                // RedirectAttributes에 입력 데이터를 추가
                redirectAttributes.addFlashAttribute("signUpForm", signUpFormDto);
                redirectAttributes.addFlashAttribute("errorMessage", "OTP 확인이 필요합니다.");
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

        if(accountService.checkId(userId))
        {
            return ApiResponse.ERROR(CommonErrorCode.ACCOUNT_ALREADY_EXIST);
        }

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
            //----------------------------------------------------------------------------------------------------------
            // 100000부터 999999 사이의 랜덤 숫자 생성
            //----------------------------------------------------------------------------------------------------------
            Random  random          = new Random();
            int     sixDigitNumber  = 100000 + random.nextInt(900000);
            smsService.sendOne(phone, "인증번호는 [" + sixDigitNumber + "] 입니다.");
            //----------------------------------------------------------------------------------------------------------

            //----------------------------------------------------------------------------------------------------------
            // 세선에 OTP 번호를 저장
            //----------------------------------------------------------------------------------------------------------
            SignUpCheckSession joinCheckSession = (SignUpCheckSession)session.getAttribute("SignUpCheckSession");
            joinCheckSession.setOtpNumber(String.valueOf(sixDigitNumber));
            log.info("Transaction ID : {}", joinCheckSession.getUuid());
        }
        else
        {
            return ApiResponse.ERROR(CommonErrorCode.INVALID_STATE);
        }

        return ApiResponse.OK("OTP 전송 완료");
    }

    @PostMapping("/verifyOtp")
    @ResponseBody
    public ApiResponse<Object> verifyOtp(@RequestParam("otp") String otp, HttpSession session)
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

                SignUpOtpRespDto respDto = new SignUpOtpRespDto(joinCheckSession.getUuid());
                return ApiResponse.OK(respDto);
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
