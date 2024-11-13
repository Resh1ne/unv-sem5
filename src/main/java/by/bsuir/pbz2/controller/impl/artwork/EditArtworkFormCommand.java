package by.bsuir.pbz2.controller.impl.artwork;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ArtistService;
import by.bsuir.pbz2.service.ArtworkService;
import by.bsuir.pbz2.service.dto.ArtistDto;
import by.bsuir.pbz2.service.dto.ArtworkDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EditArtworkFormCommand implements Command {
    private final ArtistService artistService;
    private final ArtworkService artworkService;

    @Override
    public String execute(HttpServletRequest req) {
        String rawId = req.getParameter("id");
        long id = Long.parseLong(rawId);
        ArtworkDto artworkDto = artworkService.getById(id);
        List<ArtistDto> artists = artistService.getAll();
        req.setAttribute("artwork", artworkDto);
        req.setAttribute("artists", artists);
        return "jsp/artwork/edit_artwork_form.jsp";
    }
}
