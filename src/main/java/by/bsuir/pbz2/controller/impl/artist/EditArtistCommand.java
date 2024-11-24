package by.bsuir.pbz2.controller.impl.artist;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ArtistService;
import by.bsuir.pbz2.service.dto.ArtistDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;

@Log4j2
@RequiredArgsConstructor
public class EditArtistCommand implements Command {
    private final ArtistService artistService;

    @Override
    public String execute(HttpServletRequest req) {
        log.info("Processing EditArtistCommand with request URI: {}", req.getRequestURI());

        ArtistDto artistDto;
        try {
            artistDto = process(req);
            log.info("Processed artist data for editing: {}", artistDto);
        } catch (Exception e) {
            log.error("Error processing artist data from request: {}", e.getMessage(), e);
            req.setAttribute("errorMessage", "Invalid artist data provided.");
            return "jsp/error.jsp";
        }

        try {
            ArtistDto updatedArtist = artistService.update(artistDto);
            req.setAttribute("artist", updatedArtist);
            log.info("Artist successfully updated: {}", updatedArtist);
        } catch (Exception e) {
            log.error("Failed to update artist: {}", artistDto.getId(), e);
            req.setAttribute("errorMessage", "Failed to update artist.");
            return "jsp/error.jsp";
        }

        log.info("EditArtistCommand execution completed successfully.");
        return "jsp/artist/artist.jsp";
    }

    private static ArtistDto process(HttpServletRequest req) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String birthPlace = req.getParameter("birth_place");
        String birthDate = req.getParameter("birth_date");
        String biography = req.getParameter("biography");
        String education = req.getParameter("education");

        ArtistDto artistDto = new ArtistDto();
        artistDto.setId(Long.parseLong(id));
        artistDto.setName(name);
        artistDto.setBirthPlace(birthPlace);
        artistDto.setBirthDate(LocalDate.parse(birthDate));
        artistDto.setBiography(biography);
        artistDto.setEducation(education);

        log.debug("Processed artist data: id={}, name={}, birthPlace={}, birthDate={}, biography={}, education={}",
                id, name, birthPlace, birthDate, biography, education);

        return artistDto;
    }
}