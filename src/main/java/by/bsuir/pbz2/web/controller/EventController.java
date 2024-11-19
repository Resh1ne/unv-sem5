package by.bsuir.pbz2.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/events")
public class EventController {

    @GetMapping("/screen-share")
    public String showScreenSharePage() {
        return "screenShare"; // JSP-страница для отображения видео
    }
}
