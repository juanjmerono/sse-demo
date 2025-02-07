package com.example.sse.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WebController {
    
    @GetMapping("/")
    public String getHome(Model model) {
        return "home";
    }
    

}
