package com.nos.home.user.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class AccountController {

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "index";
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
}
