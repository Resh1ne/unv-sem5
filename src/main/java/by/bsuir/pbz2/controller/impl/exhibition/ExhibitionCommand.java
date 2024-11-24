package by.bsuir.pbz2.controller.impl.exhibition;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionService;
import by.bsuir.pbz2.service.dto.ExhibitionDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
public class ExhibitionCommand implements Command {
    private final ExhibitionService exhibitionService;

    @Override
    public String execute(HttpServletRequest req) {
        String idRaw = req.getParameter("id");
        long id = Long.parseLong(idRaw);

        log.info("Fetching exhibition with ID: {}", id);

        ExhibitionDto exhibition = exhibitionService.getById(id);
        req.setAttribute("exhibition", exhibition);
        req.setAttribute("date", LocalDateTime.now().toString());

        log.info("Exhibition with ID: {} fetched successfully", exhibition.getId());

        return "jsp/exhibition/exhibition.jsp";
    }
}
