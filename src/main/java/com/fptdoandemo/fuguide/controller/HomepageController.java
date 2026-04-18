package com.fptdoandemo.fuguide.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomepageController {
    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }
}
