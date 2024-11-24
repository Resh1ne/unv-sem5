package by.bsuir.pbz2.controller.impl.artist;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ArtistService;
import by.bsuir.pbz2.service.dto.ArtistDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
public class ArtistCommand implements Command {
    private final ArtistService artistService;

    @Override
    public String execute(HttpServletRequest req) {
        log.info("Processing ArtistCommand with request URI: {}", req.getRequestURI());

        String idRaw = req.getParameter("id");
        long id;
        try {
            id = Long.parseLong(idRaw);
        } catch (NumberFormatException e) {
            log.error("Invalid artist ID format: {}", idRaw, e);
            req.setAttribute("errorMessage", "Invalid artist ID.");
            return "jsp/error.jsp";
        }

        try {
            ArtistDto artist = artistService.getById(id);
            req.setAttribute("artist", artist);
        } catch (Exception e) {
            log.error("Failed to retrieve artist with ID: {}", id, e);
            req.setAttribute("errorMessage", "Unable to retrieve artist information.");
            return "jsp/error.jsp";
        }

        req.setAttribute("date", LocalDateTime.now().toString());
        log.info("ArtistCommand execution completed successfully.");
        return "jsp/artist/artist.jsp";
    }
}