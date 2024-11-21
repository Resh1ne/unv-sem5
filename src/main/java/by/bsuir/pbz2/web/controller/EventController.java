package by.bsuir.pbz2.web.controller;

import by.bsuir.pbz2.data.entity.Event;
import by.bsuir.pbz2.data.entity.User;
import by.bsuir.pbz2.service.EventService;
import by.bsuir.pbz2.service.UserService;
import by.bsuir.pbz2.service.dto.EventDto;
import by.bsuir.pbz2.service.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final UserService userService;

    @GetMapping("/screen-share/{accessKey}")
    public String joinScreenShare(@PathVariable String accessKey, HttpSession session, Model model) {
        EventDto event = eventService.getEventByAccessKey(accessKey);
        if (event == null) {
            return "error"; // Страница ошибки
        }

        session.setAttribute("currentEvent", event);
        session.setAttribute("isHost", event.getHost().getId().equals(session.getAttribute("userId")));
        model.addAttribute("accessKey", accessKey);
        return "screenShare"; // JSP-страница с трансляцией
    }

    @GetMapping("/create")
    public String createEventForm() {
        return "create_event_form";
    }

    @PostMapping("/create")
    public ResponseEntity<String> createEvent(@ModelAttribute @RequestBody EventDto event, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(403).body("User not authenticated");
        }
        UserDto host = userService.getById(userId);
        event.setHost(host);
        EventDto eventCreated = eventService.create(event);
        return ResponseEntity.ok("Event created with access key: " + eventCreated.getAccessKey());
    }

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
