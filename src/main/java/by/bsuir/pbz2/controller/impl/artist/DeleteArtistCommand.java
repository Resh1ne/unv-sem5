package by.bsuir.pbz2.controller.impl.artist;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ArtistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteArtistCommand implements Command {
    private final ArtistService artistService;

    @Override
    public String execute(HttpServletRequest req) {
        String rawId = req.getParameter("id");
        long id = Long.parseLong(rawId);
        artistService.delete(id);
        return "jsp/delete_object.jsp";
    }
}
