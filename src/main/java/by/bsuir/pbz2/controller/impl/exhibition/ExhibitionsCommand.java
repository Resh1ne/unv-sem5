package by.bsuir.pbz2.controller.impl.exhibition;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionService;
import by.bsuir.pbz2.service.dto.ExhibitionDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class ExhibitionsCommand implements Command {
    private final ExhibitionService exhibitionService;

    @Override
    public String execute(HttpServletRequest req) {
        log.info("Fetching all exhibitions from the service");

        // Получаем все выставки
        List<ExhibitionDto> exhibitions = exhibitionService.getAll();

        // Устанавливаем атрибуты для передачи в JSP
        req.setAttribute("exhibitions", exhibitions);

        log.info("Fetched {} exhibitions successfully", exhibitions.size());

        return "jsp/exhibition/exhibitions.jsp";
    }
}
