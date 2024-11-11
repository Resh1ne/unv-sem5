package by.bsuir.pbz2.controller.impl.exhibition_hall;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionHallService;
import by.bsuir.pbz2.service.OwnerService;
import by.bsuir.pbz2.service.dto.ExhibitionHallDto;
import by.bsuir.pbz2.service.dto.OwnerDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EditExhibitionHallFormCommand implements Command {
    private final ExhibitionHallService exhibitionHallService;
    private final OwnerService ownerService;


    @Override
    public String execute(HttpServletRequest req) {
        String rawId = req.getParameter("id");
        long id = Long.parseLong(rawId);
        ExhibitionHallDto exhibitionHall = exhibitionHallService.getById(id);
        List<OwnerDto> owners = ownerService.getAll();
        req.setAttribute("exhibition", exhibitionHall);
        req.setAttribute("owners", owners);
        return "jsp/exhibition_hall/edit_exhibition_hall_form.jsp";
    }
}
