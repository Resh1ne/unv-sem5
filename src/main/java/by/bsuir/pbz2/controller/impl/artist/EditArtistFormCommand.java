package by.bsuir.pbz2.controller.impl.artist;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ArtistService;
import by.bsuir.pbz2.service.dto.ArtistDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EditArtistFormCommand implements Command {
    private final ArtistService artistService;

    @Override
    public String execute(HttpServletRequest req) {
        String rawId = req.getParameter("id");
        long id = Long.parseLong(rawId);
        ArtistDto artistDto = artistService.getById(id);
        req.setAttribute("artist", artistDto);
        return "jsp/artist/edit_artist_form.jsp";
    }
}
