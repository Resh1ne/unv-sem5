package by.bsuir.pbz2.controller.impl.exhibition;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionService;
import by.bsuir.pbz2.service.dto.CurrentExhibitionDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class CurExhibitionsCommand implements Command {
    private final ExhibitionService exhibitionService;

    @Override
    public String execute(HttpServletRequest req) {
        log.info("Starting CurExhibitionsCommand execution.");

        List<CurrentExhibitionDto> exhibitions = exhibitionService.getCurrentExhibition();

        req.setAttribute("exhibitions", exhibitions);
        log.info("Fetched {} current exhibitions.", exhibitions.size());

        log.info("CurExhibitionsCommand execution completed.");
        return "jsp/exhibition/cur_exhibitions.jsp";
    }
}
