package by.bsuir.pbz2.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
@WebServlet("/controller")
public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page;
        try {
            String command = req.getParameter("command");
            Command controller = CommandFactory.INSTANCE.get(command);
            page = controller.execute(req);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            page = CommandFactory.INSTANCE.get("error").execute(req);
        }
        req.getRequestDispatcher(page).forward(req, resp);
    }
}
