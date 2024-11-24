package by.bsuir.pbz2.controller.impl.artist;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ArtistService;
import by.bsuir.pbz2.service.dto.ArtistDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class ArtistsCommand implements Command {
    private final ArtistService artistService;

    @Override
    public String execute(HttpServletRequest req) {
        log.info("Processing ArtistsCommand with request URI: {}", req.getRequestURI());

        List<ArtistDto> artists;
        try {
            artists = artistService.getAll();
            req.setAttribute("artists", artists);
            log.info("Successfully retrieved {} artists.", artists.size());
        } catch (Exception e) {
            log.error("Failed to retrieve list of artists.", e);
            req.setAttribute("errorMessage", "Unable to retrieve list of artists.");
            return "jsp/error.jsp";
        }

        log.info("ArtistsCommand execution completed successfully.");
        return "jsp/artist/artists.jsp";
    }
}
