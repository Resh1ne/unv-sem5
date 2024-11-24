package by.bsuir.pbz2.controller.impl.artwork;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ArtworkService;
import by.bsuir.pbz2.service.dto.ArtworkDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
public class ArtworkCommand implements Command {
    private final ArtworkService artworkService;

    @Override
    public String execute(HttpServletRequest req) {
        log.info("Processing ArtworkCommand with request URI: {}", req.getRequestURI());

        String idRaw = req.getParameter("id");
        long id;
        try {
            id = Long.parseLong(idRaw);
            log.info("Parsed artwork ID: {}", id);
        } catch (NumberFormatException e) {
            log.error("Invalid artwork ID provided: {}", idRaw, e);
            req.setAttribute("errorMessage", "Invalid artwork ID.");
            return "jsp/error.jsp";
        }

        ArtworkDto artwork;
        try {
            artwork = artworkService.getById(id);
            req.setAttribute("artwork", artwork);
            req.setAttribute("date", LocalDateTime.now().toString());
            log.info("Retrieved artwork: {}", artwork);
        } catch (Exception e) {
            log.error("Failed to retrieve artwork with ID: {}", id, e);
            req.setAttribute("errorMessage", "Artwork not found.");
            return "jsp/error.jsp";
        }

        log.info("ArtworkCommand execution completed successfully.");
        return "jsp/artwork/artwork.jsp";
    }
}
