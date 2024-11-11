package by.bsuir.pbz2.controller.impl.exhibition_hall;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionHallService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteExhibitionHallCommand implements Command {
    private final ExhibitionHallService exhibitionHallService;

    @Override
    public String execute(HttpServletRequest req) {
        String rawId = req.getParameter("id");
        long id = Long.parseLong(rawId);
        exhibitionHallService.delete(id);
        return "jsp/exhibition_hall/delete_exhibition_hall.jsp";
    }
}
