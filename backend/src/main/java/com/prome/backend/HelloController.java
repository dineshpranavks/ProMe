package com.prome.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String Hello() {
        return "HI Hello Welcome to the Website";
    }
}
