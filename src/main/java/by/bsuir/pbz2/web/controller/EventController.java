package by.bsuir.pbz2.web.controller;

import by.bsuir.pbz2.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    @GetMapping("/screen-share")
    public String showScreenSharePage() {
        return "screenShare"; // JSP-страница для отображения видео
    }

    @GetMapping("/create")
    public String createEventForm() {
        return "create_event_form";
    }

//    @PostMapping("/create")
//    public String createUser(@ModelAttribute UserDto user) {
//        UserDto dto = userService.create(user);
//        return "redirect:/users/" + dto.getId();
//    }

    @GetMapping("/join")
    public String createUserForm() {
        return "create_join_form";
    }

//    @PostMapping("/join")
//    public String createUser(@ModelAttribute UserDto user) {
//        UserDto dto = userService.create(user);
//        return "redirect:/users/" + dto.getId();
//    }
}
