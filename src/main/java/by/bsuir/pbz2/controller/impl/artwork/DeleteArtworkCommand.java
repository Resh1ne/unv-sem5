package by.bsuir.pbz2.controller.impl.artwork;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ArtworkService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class DeleteArtworkCommand implements Command {
    private final ArtworkService artworkService;

    @Override
    public String execute(HttpServletRequest req) {
        log.info("Starting DeleteArtworkCommand execution.");

        String rawId = req.getParameter("id");
        try {
            long id = Long.parseLong(rawId);
            log.debug("Parsed artwork ID for deletion: {}", id);

            artworkService.delete(id);
            log.info("Artwork with ID {} successfully deleted.", id);
        } catch (NumberFormatException e) {
            log.error("Invalid artwork ID provided: '{}'", rawId, e);
            req.setAttribute("errorMessage", "Invalid ID format.");
            return "jsp/error.jsp";
        } catch (Exception e) {
            log.error("Failed to delete artwork with ID: '{}'", rawId, e);
            req.setAttribute("errorMessage", "Failed to delete artwork.");
            return "jsp/error.jsp";
        }

        log.info("DeleteArtworkCommand execution completed successfully.");
        return "jsp/delete_object.jsp";
    }
}
