package com.nos.home.module.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {

    @GetMapping("/{slug}/write")
    @ResponseBody
    public String write(@PathVariable("slug") String slug) {
        log.debug("게시판 모듈 : {} 글쓰기 페이지", slug);

        return "게시판 글쓰기 페이지";
    }

}
