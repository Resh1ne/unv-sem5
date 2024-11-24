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
        log.info("Processing GET request for URI: {}", req.getRequestURI());
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Processing POST request for URI: {}", req.getRequestURI());
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page;
        try {
            String command = req.getParameter("command");
            if (command == null || command.isEmpty()) {
                log.warn("No 'command' parameter found in request for URI: {}", req.getRequestURI());
            } else {
                log.debug("Received command: {}", command);
            }

            Command controller = CommandFactory.INSTANCE.get(command);
            log.debug("Executing controller: {}", controller.getClass().getSimpleName());
            page = controller.execute(req);

            log.info("Forwarding to page: {}", page);
        } catch (Exception e) {
            log.error("Exception occurred while processing request for URI: {}", req.getRequestURI(), e);
            page = CommandFactory.INSTANCE.get("error").execute(req);
            log.info("Forwarding to error page: {}", page);
        }

        req.getRequestDispatcher(page).forward(req, resp);
    }
}