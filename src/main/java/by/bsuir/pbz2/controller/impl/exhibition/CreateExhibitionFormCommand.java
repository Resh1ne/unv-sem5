package by.bsuir.pbz2.controller.impl.exhibition;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionHallService;
import by.bsuir.pbz2.service.dto.ExhibitionHallDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;
@RequiredArgsConstructor
public class CreateExhibitionFormCommand implements Command {
    private final ExhibitionHallService exhibitionHallService;
    @Override
    public String execute(HttpServletRequest req) {
        List<ExhibitionHallDto> exhibitions = exhibitionHallService.getAll();
        req.setAttribute("exhibition_halls", exhibitions);
        return "jsp/exhibition/create_exhibition_form.jsp";
    }
}
