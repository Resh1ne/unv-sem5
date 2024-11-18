package by.bsuir.pbz2.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ScreenShareController {

    @GetMapping("/screen-share")
    public String showScreenSharePage() {
        return "screenShare"; // JSP-страница для отображения видео
    }
}
