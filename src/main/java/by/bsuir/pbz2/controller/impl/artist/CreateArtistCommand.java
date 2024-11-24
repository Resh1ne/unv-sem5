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
public class CreateArtistCommand implements Command {
    private final ArtistService artistService;

    @Override
    public String execute(HttpServletRequest req) {
        log.info("Processing CreateArtistCommand with request URI: {}", req.getRequestURI());

        ArtistDto artistDto;
        try {
            artistDto = process(req);
            log.info("Artist data processed successfully: {}", artistDto);
        } catch (Exception e) {
            log.error("Failed to process artist data from request.", e);
            req.setAttribute("errorMessage", "Invalid artist data.");
            return "jsp/error.jsp";
        }

        ArtistDto artistDtoCreated;
        try {
            artistDtoCreated = artistService.create(artistDto);
            log.info("Artist created successfully with ID: {}", artistDtoCreated.getId());
        } catch (Exception e) {
            log.error("Failed to create artist.", e);
            req.setAttribute("errorMessage", "Unable to create artist.");
            return "jsp/error.jsp";
        }

        req.setAttribute("artist", artistDtoCreated);
        log.info("CreateArtistCommand execution completed successfully.");
        return "jsp/artist/artist.jsp";
    }

    private static ArtistDto process(HttpServletRequest req) {
        String name = req.getParameter("name");
        String birthPlace = req.getParameter("birth_place");
        String birthDate = req.getParameter("birth_date");
        String biography = req.getParameter("biography");
        String education = req.getParameter("education");

        ArtistDto artistDto = new ArtistDto();
        artistDto.setName(name);
        artistDto.setBirthPlace(birthPlace);
        artistDto.setBirthDate(LocalDate.parse(birthDate));
        artistDto.setBiography(biography);
        artistDto.setEducation(education);

        return artistDto;
    }
}