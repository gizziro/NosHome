package com.nos.home.module.blog.controller;

import com.nos.home.common.module.dto.ModuleInstanceDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/BLOG")
@Slf4j
public class BlogController {

    @GetMapping("/{slug}/")
    @ResponseBody
    public String list(@PathVariable("slug") String slug, HttpServletRequest request) {
        log.debug("블로그 모듈 : {} 목록 페이지", slug);

        ModuleInstanceDto instanceDto = (ModuleInstanceDto)request.getAttribute("moduleInstance");


        return instanceDto.getInstanceName() +  " --> 블로그 페이지입니다. : "+ instanceDto.getInstanceId();
    }
}
