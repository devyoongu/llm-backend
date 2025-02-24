package com.llm.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class IndexController {

    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        return "index";
    }

    @RequestMapping("/tables")
    public String table(Model model) {
        log.info("tables controller");


        return "tables";
    }
}
