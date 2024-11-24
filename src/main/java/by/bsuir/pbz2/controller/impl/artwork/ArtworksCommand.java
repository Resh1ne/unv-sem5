package by.bsuir.pbz2.controller.impl.artwork;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ArtworkService;
import by.bsuir.pbz2.service.dto.ArtworkDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class ArtworksCommand implements Command {
    private final ArtworkService artworkService;

    @Override
    public String execute(HttpServletRequest req) {
        log.info("Processing ArtworksCommand with request URI: {}", req.getRequestURI());

        List<ArtworkDto> artworks;
        try {
            artworks = artworkService.getAll();
            req.setAttribute("artworks", artworks);
            log.info("Retrieved {} artworks.", artworks.size());
        } catch (Exception e) {
            log.error("Failed to retrieve artworks.", e);
            req.setAttribute("errorMessage", "Failed to load artworks.");
            return "jsp/error.jsp";
        }

        log.info("ArtworksCommand execution completed successfully.");
        return "jsp/artwork/artworks.jsp";
    }
}
