package by.bsuir.pbz2.controller.impl;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionHallService;
import by.bsuir.pbz2.service.dto.ExhibitionHallDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ExhibitionHallsCommand implements Command {
    private final ExhibitionHallService exhibitionHallService;
    @Override
    public String execute(HttpServletRequest req) {
        List<ExhibitionHallDto> exhibitionHalls = exhibitionHallService.getAll();
        req.setAttribute("exhibition_halls", exhibitionHalls);
        return "jsp/exhibition_hall/exhibition_halls.jsp";
    }
}
