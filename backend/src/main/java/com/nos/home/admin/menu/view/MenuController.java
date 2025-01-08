package com.nos.home.admin.menu.view;

import com.nos.home.common.module.BaseModule;
import com.nos.home.common.util.ModuleHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/menu")
@RequiredArgsConstructor
public class MenuController
{
    private final ModuleHelper moduleHelper;

    @GetMapping("/register")
    public String viewMenuRegister(Model model)
    {
        //--------------------------------------------------------------------------------------------------------------
        // - [서비스 모듈 목록 지정]
        //--------------------------------------------------------------------------------------------------------------
        model.addAttribute("modules", moduleHelper.getModuleDtos(BaseModule.ModuleType.SERVICE));

        //--------------------------------------------------------------------------------------------------------------
        // -
        //--------------------------------------------------------------------------------------------------------------

        return "admin/menu/menu";
    }
}
