package com.sadat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/yes")
    public String allowed(){

        return "YES";
    }

    @GetMapping("/no")
    public String disallowed(){

        return "NO";
    }
}
