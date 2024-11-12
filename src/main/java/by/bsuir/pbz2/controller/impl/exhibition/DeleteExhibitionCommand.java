package by.bsuir.pbz2.controller.impl.exhibition;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteExhibitionCommand implements Command {
    private final ExhibitionService exhibitionService;

    @Override
    public String execute(HttpServletRequest req) {
        String rawId = req.getParameter("id");
        long id = Long.parseLong(rawId);
        exhibitionService.delete(id);
        return "jsp/delete_object.jsp";
    }
}
