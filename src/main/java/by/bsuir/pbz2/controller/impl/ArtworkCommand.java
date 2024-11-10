package by.bsuir.pbz2.controller.impl;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ArtworkService;
import by.bsuir.pbz2.service.dto.ArtworkDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ArtworkCommand implements Command {
    private final ArtworkService artworkService;

    @Override
    public String execute(HttpServletRequest req) {
        String idRaw = req.getParameter("id");
        long id = Long.parseLong(idRaw);
        ArtworkDto artwork = artworkService.getById(id);
        req.setAttribute("artwork", artwork);
        req.setAttribute("date", LocalDateTime.now().toString());
        return "jsp/artwork/artwork.jsp";
    }
}
