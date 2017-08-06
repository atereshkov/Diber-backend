package com.github.handioq.diber.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "Hey. What's up? What do you want?";
    }
}