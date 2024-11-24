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
public class EditArtworkCommand implements Command {
    private final ArtistService artistService;
    private final ArtworkService artworkService;

    @Override
    public String execute(HttpServletRequest req) {
        log.info("Starting EditArtworkCommand execution.");

        ArtworkDto artworkDto = process(req, artistService);

        ArtworkDto artworkDtoUpdated = artworkService.update(artworkDto);

        req.setAttribute("artwork", artworkDtoUpdated);
        log.info("Artwork with ID {} successfully updated.", artworkDto.getId());

        log.info("EditArtworkCommand execution completed successfully.");
        return "jsp/artwork/artwork.jsp";
    }

    private static ArtworkDto process(HttpServletRequest req, ArtistService artistService) {
        long id = Long.parseLong(req.getParameter("id"));
        log.debug("Processing artwork with ID: {}", id);

        String title = req.getParameter("title");
        String executionType = req.getParameter("execution_type");
        String creationDate = req.getParameter("creation_date");

        String heightString = req.getParameter("height");
        BigDecimal height = Objects.equals(heightString, "") ? BigDecimal.valueOf(0) : new BigDecimal(heightString);

        String widthString = req.getParameter("width");
        BigDecimal width = Objects.equals(widthString, "") ? BigDecimal.valueOf(0) : new BigDecimal(widthString);

        String volumeString = req.getParameter("volume");
        BigDecimal volume = Objects.equals(volumeString, "") ? BigDecimal.valueOf(0) : new BigDecimal(volumeString);

        ArtistDto artistDto = artistService.getById(Long.parseLong(req.getParameter("artist")));

        ArtworkDto artworkDto = new ArtworkDto();
        artworkDto.setId(id);
        artworkDto.setTitle(title);
        artworkDto.setExecutionType(ExecutionType.valueOf(executionType));
        artworkDto.setCreationDate(LocalDate.parse(creationDate));
        artworkDto.setHeight(height);
        artworkDto.setWidth(width);
        artworkDto.setVolume(volume);
        artworkDto.setArtistId(artistDto);

        log.debug("Artwork DTO created with title: {}", title);
        return artworkDto;
    }
}
