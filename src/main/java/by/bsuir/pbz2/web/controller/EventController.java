package by.bsuir.pbz2.web.controller;

import by.bsuir.pbz2.service.EventParticipantService;
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
    private final EventParticipantService eventParticipantService;

    @GetMapping("/screen-share/{accessKey}")
    public String joinScreenShare(@PathVariable String accessKey, HttpSession session, Model model) {
        EventDto event = eventService.getEventByAccessKey(accessKey);
        if (event == null) {
            return "error"; // Страница ошибки
        }
        // Сохраняем информацию о текущем событии и пользователе в HTTP-сессии
        session.setAttribute("currentEvent", event);
        session.setAttribute("userId", session.getAttribute("userId"));
        session.setAttribute("isHost", event.getHost().getId().equals(session.getAttribute("userId")));


        model.addAttribute("accessKey", accessKey);
        model.addAttribute("isHost", event.getHost().getId().equals(session.getAttribute("userId")));
        model.addAttribute("username", userService.getById(event.getHost().getId()).getUsername());
        model.addAttribute("title", event.getTitle());
        model.addAttribute("description", event.getDescription());
        return "screenShare"; // JSP-страница с трансляцией
    }

    // Обработчик POST-запроса для выхода из трансляции
    @GetMapping("/screen-share/{accessKey}/exit")
    public String exitStream(@PathVariable String accessKey) {
        EventDto event = eventService.getEventByAccessKey(accessKey);
        eventService.delete(event.getId());
        return "index";
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
        // Получаем accessKey созданного мероприятия
        String accessKey = eventCreated.getAccessKey();

        // Перенаправляем пользователя на страницу мероприятия с accessKey
        return ResponseEntity.status(302).header("Location", "/events/screen-share/" + accessKey).build();
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
