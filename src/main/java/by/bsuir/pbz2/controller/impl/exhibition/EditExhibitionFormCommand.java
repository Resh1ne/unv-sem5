package by.bsuir.pbz2.controller.impl.exhibition;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionHallService;
import by.bsuir.pbz2.service.ExhibitionService;
import by.bsuir.pbz2.service.dto.ExhibitionDto;
import by.bsuir.pbz2.service.dto.ExhibitionHallDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EditExhibitionFormCommand implements Command {
    private final ExhibitionService exhibitionService;
    private final ExhibitionHallService exhibitionHallService;

    @Override
    public String execute(HttpServletRequest req) {
        String rawId = req.getParameter("id");
        long id = Long.parseLong(rawId);
        ExhibitionDto exhibition = exhibitionService.getById(id);
        List<ExhibitionHallDto> exhibitionHalls = exhibitionHallService.getAll();
        req.setAttribute("exhibition", exhibition);
        req.setAttribute("exhibition_halls", exhibitionHalls);
        return "jsp/exhibition/edit_exhibition_form.jsp";
    }
}
