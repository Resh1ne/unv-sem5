package by.bsuir.pbz2.controller.impl.exhibition;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.ExhibitionService;
import by.bsuir.pbz2.service.dto.CurrentExhibitionDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CurExhibitionsCommand implements Command {
    private final ExhibitionService exhibitionService;

    @Override
    public String execute(HttpServletRequest req) {
        List<CurrentExhibitionDto> exhibitions = exhibitionService.getCurrentExhibition();
        req.setAttribute("exhibitions", exhibitions);
        return "jsp/exhibition/cur_exhibitions.jsp";
    }
}
