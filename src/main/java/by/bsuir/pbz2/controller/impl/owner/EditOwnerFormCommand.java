package by.bsuir.pbz2.controller.impl.owner;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.OwnerService;
import by.bsuir.pbz2.service.dto.OwnerDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EditOwnerFormCommand implements Command {
    private final OwnerService ownerService;

    @Override
    public String execute(HttpServletRequest req) {
        String rawId = req.getParameter("id");
        long id = Long.parseLong(rawId);
        OwnerDto owner = ownerService.getById(id);
        req.setAttribute("owner", owner);
        return "jsp/owner/edit_owner_form.jsp";
    }
}
