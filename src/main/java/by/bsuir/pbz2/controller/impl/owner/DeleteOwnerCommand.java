package by.bsuir.pbz2.controller.impl.owner;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.OwnerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteOwnerCommand implements Command {
    private final OwnerService ownerService;

    @Override
    public String execute(HttpServletRequest req) {
        String rawId = req.getParameter("id");
        long id = Long.parseLong(rawId);
        ownerService.delete(id);
        return "jsp/delete_object.jsp";
    }
}
