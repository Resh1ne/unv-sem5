package by.bsuir.pbz2.controller.impl.artwork;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ArtworkService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteArtworkCommand implements Command {
    private final ArtworkService artworkService;

    @Override
    public String execute(HttpServletRequest req) {
        String rawId = req.getParameter("id");
        long id = Long.parseLong(rawId);
        artworkService.delete(id);
        return "jsp/delete_object.jsp";
    }
}
