package by.bsuir.pbz2.controller.impl.exhibition;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionService;
import by.bsuir.pbz2.service.dto.ExhibitionDto;
import by.bsuir.pbz2.service.dto.ExhibitionParticipantsAndArtworksDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class ExhibitionParAndArtCommand implements Command {
    private final ExhibitionService exhibitionService;

    @Override
    public String execute(HttpServletRequest req) {
        String idRaw = req.getParameter("id");
        long id = Long.parseLong(idRaw);

        log.info("Fetching participants and artworks for exhibition with ID: {}", id);

        // Получаем список участников и произведений искусства для выставки
        List<ExhibitionParticipantsAndArtworksDto> exhibitions = exhibitionService.getParticipantsArtworksByExhibitionId(id);

        // Получаем информацию о выставке
        ExhibitionDto exhibition = exhibitionService.getById(id);

        // Устанавливаем атрибуты для передачи в JSP
        req.setAttribute("exhibitions", exhibitions);
        req.setAttribute("exhibition", exhibition);

        log.info("Exhibition with ID: {} fetched successfully with participants and artworks", exhibition.getId());

        return "jsp/exhibition/exhibition_par_and_art.jsp";
    }
}
