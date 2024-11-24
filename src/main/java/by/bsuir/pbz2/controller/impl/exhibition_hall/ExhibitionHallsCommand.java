package by.bsuir.pbz2.controller.impl.exhibition_hall;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionHallService;
import by.bsuir.pbz2.service.dto.ExhibitionHallDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class ExhibitionHallsCommand implements Command {
    private final ExhibitionHallService exhibitionHallService;

    @Override
    public String execute(HttpServletRequest req) {
        log.info("Fetching all exhibition halls.");

        List<ExhibitionHallDto> exhibitionHalls = exhibitionHallService.getAll();

        if (exhibitionHalls.isEmpty()) {
            log.warn("No exhibition halls found.");
        } else {
            log.info("Found {} exhibition halls.", exhibitionHalls.size());
        }

        req.setAttribute("exhibition_halls", exhibitionHalls);
        return "jsp/exhibition_hall/exhibition_halls.jsp";
    }
}
