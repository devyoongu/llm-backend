package com.llm.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HmoeController {

    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        return "home";
    }
}
