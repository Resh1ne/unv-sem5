package by.bsuir.pbz2.controller.impl.exhibition_hall;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionHallService;
import by.bsuir.pbz2.service.OwnerService;
import by.bsuir.pbz2.service.dto.ExhibitionHallDto;
import by.bsuir.pbz2.service.dto.OwnerDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@RequiredArgsConstructor
public class EditExhibitionHallCommand implements Command {
    private final ExhibitionHallService exhibitionHallService;
    private final OwnerService ownerService;

    @Override
    public String execute(HttpServletRequest req) {
        ExhibitionHallDto exhibitionHallDto = process(req, ownerService);

        ExhibitionHallDto exhibitionHallDtoCreated = exhibitionHallService.update(exhibitionHallDto);

        req.setAttribute("exhibition", exhibitionHallDtoCreated);
        return "jsp/exhibition_hall/exhibition_hall.jsp";
    }

    private static ExhibitionHallDto process(HttpServletRequest req, OwnerService ownerService) {
        long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        String areaString = req.getParameter("area");
        BigDecimal area;
        if (Objects.equals(areaString, "")) {
            area = BigDecimal.valueOf(0);
        } else {
            area = new BigDecimal(req.getParameter("area"));
        }
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        OwnerDto owner = ownerService.getById(Long.parseLong(req.getParameter("ownerId")));
        ExhibitionHallDto exhibitionHallDto = new ExhibitionHallDto();
        exhibitionHallDto.setId(id);
        exhibitionHallDto.setName(name);
        exhibitionHallDto.setArea(area);
        exhibitionHallDto.setAddress(address);
        exhibitionHallDto.setPhone(phone);
        exhibitionHallDto.setOwnerId(owner);
        return exhibitionHallDto;
    }
}
