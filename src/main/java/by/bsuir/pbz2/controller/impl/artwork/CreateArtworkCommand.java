package by.bsuir.pbz2.controller.impl.artwork;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.data.entity.enums.ExecutionType;
import by.bsuir.pbz2.service.ArtistService;
import by.bsuir.pbz2.service.ArtworkService;
import by.bsuir.pbz2.service.dto.ArtistDto;
import by.bsuir.pbz2.service.dto.ArtworkDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@RequiredArgsConstructor
public class CreateArtworkCommand implements Command {
    private final ArtistService artistService;
    private final ArtworkService artworkService;

    @Override
    public String execute(HttpServletRequest req) {
        ArtworkDto artworkDto = process(req, artistService);

        ArtworkDto artworkDtoCreated = artworkService.create(artworkDto);

        req.setAttribute("artwork", artworkDtoCreated);
        return "jsp/artwork/artwork.jsp";
    }

    private static ArtworkDto process(HttpServletRequest req, ArtistService artistService) {
        String title = req.getParameter("title");
        String executionType = req.getParameter("execution_type");
        String creationDate = req.getParameter("creation_date");
        String heightString = req.getParameter("height");
        BigDecimal height;
        if (Objects.equals(heightString, "")) {
            height = BigDecimal.valueOf(0);
        } else {
            height = new BigDecimal(req.getParameter("height"));
        }
        String widthString = req.getParameter("width");
        BigDecimal width;
        if (Objects.equals(widthString, "")) {
            width = BigDecimal.valueOf(0);
        } else {
            width = new BigDecimal(req.getParameter("width"));
        }
        String volumeString = req.getParameter("volume");
        BigDecimal volume;
        if (Objects.equals(volumeString, "")) {
            volume = BigDecimal.valueOf(0);
        } else {
            volume = new BigDecimal(req.getParameter("volume"));
        }
        ArtistDto artistDto = artistService.getById(Long.parseLong(req.getParameter("artist")));

        ArtworkDto artworkDto = new ArtworkDto();
        artworkDto.setTitle(title);
        artworkDto.setExecutionType(ExecutionType.valueOf(executionType));
        artworkDto.setCreationDate(LocalDate.parse(creationDate));
        artworkDto.setHeight(height);
        artworkDto.setWidth(width);
        artworkDto.setVolume(volume);
        artworkDto.setArtistId(artistDto);
        return artworkDto;
    }
}
