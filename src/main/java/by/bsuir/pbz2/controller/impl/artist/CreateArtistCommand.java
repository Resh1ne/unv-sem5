package by.bsuir.pbz2.controller.impl.artist;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ArtistService;
import by.bsuir.pbz2.service.dto.ArtistDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class CreateArtistCommand implements Command {
    private final ArtistService artistService;

    @Override
    public String execute(HttpServletRequest req) {
        ArtistDto artistDto = process(req);
        ArtistDto artistDtoCreated = artistService.create(artistDto);
        req.setAttribute("artist", artistDtoCreated);
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
