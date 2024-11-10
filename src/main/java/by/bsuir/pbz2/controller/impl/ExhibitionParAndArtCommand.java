package by.bsuir.pbz2.controller.impl;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionService;
import by.bsuir.pbz2.service.dto.ExhibitionDto;
import by.bsuir.pbz2.service.dto.ExhibitionParticipantsAndArtworksDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ExhibitionParAndArtCommand implements Command {
    private final ExhibitionService exhibitionService;

    @Override
    public String execute(HttpServletRequest req) {
        String idRaw = req.getParameter("id");
        long id = Long.parseLong(idRaw);
        List<ExhibitionParticipantsAndArtworksDto> exhibitions = exhibitionService.getParticipantsArtworksByExhibitionId(id);
        ExhibitionDto exhibition = exhibitionService.getById(id);
        req.setAttribute("exhibitions", exhibitions);
        req.setAttribute("exhibition", exhibition);
        return "jsp/exhibition/exhibition_par_and_art.jsp";
    }
}
