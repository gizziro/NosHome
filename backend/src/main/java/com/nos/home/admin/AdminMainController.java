package com.nos.home.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminMainController {

    @GetMapping(value = {"/", "/members", "/groups", "/menus"})
    public String index(){
        return "/admin/admin_vue";
    }
}
