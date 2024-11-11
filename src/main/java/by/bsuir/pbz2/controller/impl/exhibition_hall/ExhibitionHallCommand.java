package by.bsuir.pbz2.controller.impl.exhibition_hall;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionHallService;
import by.bsuir.pbz2.service.dto.ExhibitionHallDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ExhibitionHallCommand implements Command {
    private final ExhibitionHallService exhibitionHallService;

    @Override
    public String execute(HttpServletRequest req) {
        String idRaw = req.getParameter("id");
        long id = Long.parseLong(idRaw);
        ExhibitionHallDto exhibition = exhibitionHallService.getById(id);
        req.setAttribute("exhibition", exhibition);
        req.setAttribute("date", LocalDateTime.now().toString());
        return "jsp/exhibition_hall/exhibition_hall.jsp";
    }
}
