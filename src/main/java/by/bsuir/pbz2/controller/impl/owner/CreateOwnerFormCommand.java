package by.bsuir.pbz2.controller.impl.owner;

import by.bsuir.pbz2.controller.Command;
import jakarta.servlet.http.HttpServletRequest;

public class CreateOwnerFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/owner/create_owner_form.jsp";
    }
}
