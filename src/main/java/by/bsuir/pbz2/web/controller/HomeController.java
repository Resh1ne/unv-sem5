package by.bsuir.pbz2.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
@SuppressWarnings("unused")
public class HomeController {

    @GetMapping
    public String home() {
        return "index";
    }
}