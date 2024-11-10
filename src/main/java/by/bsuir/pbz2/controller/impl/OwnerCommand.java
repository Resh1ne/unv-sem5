package by.bsuir.pbz2.controller.impl;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.OwnerService;
import by.bsuir.pbz2.service.dto.OwnerDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class OwnerCommand implements Command {
    private final OwnerService ownerService;

    @Override
    public String execute(HttpServletRequest req) {
        String idRaw = req.getParameter("id");
        long id = Long.parseLong(idRaw);
        OwnerDto owner = ownerService.getById(id);
        req.setAttribute("owner", owner);
        req.setAttribute("date", LocalDateTime.now().toString());
        return "jsp/owner/owner.jsp";
    }
}
