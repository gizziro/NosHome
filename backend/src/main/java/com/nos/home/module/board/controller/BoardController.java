package com.nos.home.module.board.controller;

import com.nos.home.common.module.dto.ModuleInstanceDto;
import com.nos.home.common.util.ModuleHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    //------------------------------------------------------------------------------------------------------------------
    // 필드 및 레포지토리
    //------------------------------------------------------------------------------------------------------------------
    private final ModuleHelper moduleHelper;



    @GetMapping("/{slug}/")
    @ResponseBody
    public String list(@PathVariable("slug") String slug, HttpServletRequest request) {
        String instanceId = (String)request.getAttribute("instanceId");
        log.debug("게시판 목록 ==> Slug : {}, instanceId : {}", slug, instanceId);

        ModuleInstanceDto instanceDto = moduleHelper.getModuleInstance(instanceId);


        return instanceDto.getInstanceName() +  " --> 게시판 목록 페이지 : "+ instanceDto.getInstanceId();
    }


    @GetMapping("/{slug}/write")
    @ResponseBody
    public String write(@PathVariable("slug") String slug) {
        log.debug("게시판 모듈 : {} 글쓰기 페이지", slug);

        return "게시판 글쓰기 페이지";
    }

}
