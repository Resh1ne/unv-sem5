package by.bsuir.pbz2.web.controller.impl;

import by.bsuir.pbz2.web.controller.Command;
import jakarta.servlet.http.HttpServletRequest;

public class ErrorCommand implements Command {
    @Override
    public String execute(HttpServletRequest req) {
        return "jsp/error/error.jsp";
    }
}
