package by.bsuir.pbz2.controller.impl.exhibition;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class DeleteExhibitionCommand implements Command {
    private final ExhibitionService exhibitionService;

    @Override
    public String execute(HttpServletRequest req) {
        String rawId = req.getParameter("id");
        long id = Long.parseLong(rawId);

        log.info("Attempting to delete exhibition with ID: {}", id);

        exhibitionService.delete(id);

        log.info("Exhibition with ID: {} deleted successfully", id);

        return "jsp/delete_object.jsp";
    }
}
