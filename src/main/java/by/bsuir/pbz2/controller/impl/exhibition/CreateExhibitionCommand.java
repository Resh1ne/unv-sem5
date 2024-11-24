package by.bsuir.pbz2.controller.impl.exhibition;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.data.entity.enums.ExhibitionType;
import by.bsuir.pbz2.service.ExhibitionHallService;
import by.bsuir.pbz2.service.ExhibitionService;
import by.bsuir.pbz2.service.dto.ExhibitionDto;
import by.bsuir.pbz2.service.dto.ExhibitionHallDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;

@Log4j2
@RequiredArgsConstructor
public class CreateExhibitionCommand implements Command {
    private final ExhibitionService exhibitionService;
    private final ExhibitionHallService exhibitionHallService;

    @Override
    public String execute(HttpServletRequest req) {
        log.info("Starting CreateExhibitionCommand execution.");

        ExhibitionDto exhibitionDto = process(req, exhibitionHallService);

        ExhibitionDto exhibitionDtoCreated = exhibitionService.create(exhibitionDto);

        req.setAttribute("exhibition", exhibitionDtoCreated);
        log.info("Exhibition with ID {} successfully created.", exhibitionDtoCreated.getId());

        log.info("CreateExhibitionCommand execution completed successfully.");
        return "jsp/exhibition/exhibition.jsp";
    }

    private static ExhibitionDto process(HttpServletRequest req, ExhibitionHallService exhibitionHallService) {
        String name = req.getParameter("name");
        log.debug("Processing exhibition with name: {}", name);

        ExhibitionHallDto exhibitionHall = exhibitionHallService.getById(Long.parseLong(req.getParameter("exhibition_hall")));
        String type = req.getParameter("type");
        String startDate = req.getParameter("start_date");
        String endDate = req.getParameter("end_date");

        ExhibitionDto exhibitionDto = new ExhibitionDto();
        exhibitionDto.setName(name);
        exhibitionDto.setHallId(exhibitionHall);
        exhibitionDto.setType(ExhibitionType.valueOf(type));
        exhibitionDto.setStartDate(LocalDate.parse(startDate));
        exhibitionDto.setEndDate(LocalDate.parse(endDate));

        log.debug("Exhibition DTO created with name: {}", name);
        return exhibitionDto;
    }
}
