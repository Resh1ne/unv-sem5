package by.bsuir.pbz2.controller.impl.exhibition_hall;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionHallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import jakarta.servlet.http.HttpServletRequest;

@Log4j2
@RequiredArgsConstructor
public class DeleteExhibitionHallCommand implements Command {
    private final ExhibitionHallService exhibitionHallService;

    @Override
    public String execute(HttpServletRequest req) {
        String rawId = req.getParameter("id");
        long id = Long.parseLong(rawId);

        log.info("Starting the deletion of exhibition hall with ID: {}", id);

        exhibitionHallService.delete(id);

        log.info("Exhibition hall with ID: {} deleted successfully", id);

        return "jsp/delete_object.jsp";
    }
}
