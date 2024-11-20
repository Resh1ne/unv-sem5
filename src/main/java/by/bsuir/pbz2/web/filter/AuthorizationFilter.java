package by.bsuir.pbz2.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class AuthorizationFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        if (!req.getRequestURI().equals("/login") && !req.getRequestURI().equals("/")
                && !req.getRequestURI().endsWith(".css") && !req.getRequestURI().equals("/users/create")) {
            if (extracted(req, res)) return;
        }
        chain.doFilter(req, res);
    }

    private static boolean extracted(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Object user = req.getSession().getAttribute("user");

        if (user == null) {
            res.sendRedirect("/login");
            return true;
        }

        return false;
    }

}