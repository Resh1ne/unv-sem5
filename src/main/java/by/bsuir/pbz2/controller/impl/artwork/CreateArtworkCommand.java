package by.bsuir.pbz2.controller.impl.artwork;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.data.entity.enums.ExecutionType;
import by.bsuir.pbz2.service.ArtistService;
import by.bsuir.pbz2.service.ArtworkService;
import by.bsuir.pbz2.service.dto.ArtistDto;
import by.bsuir.pbz2.service.dto.ArtworkDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
public class CreateArtworkCommand implements Command {
    private final ArtistService artistService;
    private final ArtworkService artworkService;

    @Override
    public String execute(HttpServletRequest req) {
        log.info("Starting CreateArtworkCommand execution.");

        ArtworkDto artworkDto;
        try {
            artworkDto = process(req, artistService);
            log.info("Processed artwork data for creation: {}", artworkDto);

            ArtworkDto artworkDtoCreated = artworkService.create(artworkDto);
            req.setAttribute("artwork", artworkDtoCreated);
            log.info("Artwork created successfully with ID: {}", artworkDtoCreated.getId());
        } catch (Exception e) {
            log.error("Failed to create artwork.", e);
            req.setAttribute("errorMessage", "Failed to create artwork.");
            return "jsp/error.jsp";
        }

        log.info("CreateArtworkCommand execution completed successfully.");
        return "jsp/artwork/artwork.jsp";
    }

    private static ArtworkDto process(HttpServletRequest req, ArtistService artistService) {
        log.debug("Processing request data for artwork creation.");

        String title = req.getParameter("title");
        String executionType = req.getParameter("execution_type");
        String creationDate = req.getParameter("creation_date");

        BigDecimal height = parseBigDecimal(req.getParameter("height"));
        BigDecimal width = parseBigDecimal(req.getParameter("width"));
        BigDecimal volume = parseBigDecimal(req.getParameter("volume"));

        ArtistDto artistDto = artistService.getById(Long.parseLong(req.getParameter("artist")));

        ArtworkDto artworkDto = new ArtworkDto();
        artworkDto.setTitle(title);
        artworkDto.setExecutionType(ExecutionType.valueOf(executionType));
        artworkDto.setCreationDate(LocalDate.parse(creationDate));
        artworkDto.setHeight(height);
        artworkDto.setWidth(width);
        artworkDto.setVolume(volume);
        artworkDto.setArtistId(artistDto);

        log.debug("Processed artwork DTO: {}", artworkDto);
        return artworkDto;
    }

    private static BigDecimal parseBigDecimal(String value) {
        if (Objects.equals(value, "") || value == null) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            log.warn("Invalid BigDecimal value provided: '{}'. Defaulting to 0.", value);
            return BigDecimal.ZERO;
        }
    }
}
