package com.nos.home.admin.account.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/permission")
public class PermissionAdminController
{
    @GetMapping("/list")
    public String page_memberList(){
        return "admin/account/permissionList";
    }
}
