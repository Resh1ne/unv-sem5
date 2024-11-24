package by.bsuir.pbz2.controller.impl.artist;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ArtistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class DeleteArtistCommand implements Command {
    private final ArtistService artistService;

    @Override
    public String execute(HttpServletRequest req) {
        log.info("Processing DeleteArtistCommand with request URI: {}", req.getRequestURI());

        String rawId = req.getParameter("id");
        long id;
        try {
            id = Long.parseLong(rawId);
            log.info("Parsed artist ID: {}", id);
        } catch (NumberFormatException e) {
            log.error("Invalid artist ID: {}", rawId, e);
            req.setAttribute("errorMessage", "Invalid artist ID.");
            return "jsp/error.jsp";
        }

        try {
            artistService.delete(id);
            log.info("Artist with ID: {} successfully deleted.", id);
        } catch (Exception e) {
            log.error("Failed to delete artist with ID: {}", id, e);
            req.setAttribute("errorMessage", "Unable to delete artist.");
            return "jsp/error.jsp";
        }

        log.info("DeleteArtistCommand execution completed successfully.");
        return "jsp/delete_object.jsp";
    }
}
