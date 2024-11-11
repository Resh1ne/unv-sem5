package by.bsuir.pbz2.controller.impl.exhibition_hall;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.OwnerService;
import by.bsuir.pbz2.service.dto.OwnerDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CreateExhibitionHallFormCommand implements Command {
    private final OwnerService ownerService;
    @Override
    public String execute(HttpServletRequest req) {
        List<OwnerDto> owners = ownerService.getAll();
        req.setAttribute("owners", owners);
        return "jsp/exhibition_hall/create_exhibition_hall_form.jsp";
    }
}
