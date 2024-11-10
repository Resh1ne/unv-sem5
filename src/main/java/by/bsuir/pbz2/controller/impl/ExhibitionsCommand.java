package by.bsuir.pbz2.controller.impl;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionService;
import by.bsuir.pbz2.service.dto.ExhibitionDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ExhibitionsCommand implements Command {
    private final ExhibitionService exhibitionService;

    @Override
    public String execute(HttpServletRequest req) {
        List<ExhibitionDto> exhibitions = exhibitionService.getAll();
        req.setAttribute("exhibitions", exhibitions);
        return "jsp/exhibition/exhibitions.jsp";
    }
}
