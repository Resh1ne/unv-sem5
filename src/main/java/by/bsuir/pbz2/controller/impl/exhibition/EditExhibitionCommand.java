package by.bsuir.pbz2.controller.impl.exhibition;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.data.entity.enums.ExhibitionType;
import by.bsuir.pbz2.service.ExhibitionHallService;
import by.bsuir.pbz2.service.ExhibitionService;
import by.bsuir.pbz2.service.dto.ExhibitionDto;
import by.bsuir.pbz2.service.dto.ExhibitionHallDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class EditExhibitionCommand implements Command {
    private final ExhibitionService exhibitionService;
    private final ExhibitionHallService exhibitionHallService;

    @Override
    public String execute(HttpServletRequest req) {
        ExhibitionDto exhibitionDto = process(req, exhibitionHallService);

        ExhibitionDto exhibitionDtoCreated = exhibitionService.update(exhibitionDto);

        req.setAttribute("exhibition", exhibitionDtoCreated);
        return "jsp/exhibition/exhibition.jsp";
    }

    private static ExhibitionDto process(HttpServletRequest req, ExhibitionHallService exhibitionHallService) {
        long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        ExhibitionHallDto exhibitionHall = exhibitionHallService.getById(Long.parseLong(req.getParameter("exhibition_hall")));
        String type = req.getParameter("type");
        String startDate = req.getParameter("start_date");
        String endDate = req.getParameter("end_date");
        ExhibitionDto exhibitionDto = new ExhibitionDto();
        exhibitionDto.setId(id);
        exhibitionDto.setName(name);
        exhibitionDto.setHallId(exhibitionHall);
        exhibitionDto.setType(ExhibitionType.valueOf(type));
        exhibitionDto.setStartDate(LocalDate.parse(startDate));
        exhibitionDto.setEndDate(LocalDate.parse(endDate));
        return exhibitionDto;
    }
}
