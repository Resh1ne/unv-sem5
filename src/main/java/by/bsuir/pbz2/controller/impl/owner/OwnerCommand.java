package by.bsuir.pbz2.controller.impl.owner;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.OwnerService;
import by.bsuir.pbz2.service.dto.OwnerDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
public class OwnerCommand implements Command {
    private final OwnerService ownerService;

    @Override
    public String execute(HttpServletRequest req) {
        String idRaw = req.getParameter("id");
        long id = Long.parseLong(idRaw);

        log.info("Fetching owner with ID: {}", id);

        OwnerDto owner = ownerService.getById(id);

        if (owner != null) {
            log.info("Owner with ID: {} fetched successfully", id);
        } else {
            log.warn("Owner with ID: {} not found", id);
        }

        req.setAttribute("owner", owner);
        req.setAttribute("date", LocalDateTime.now().toString());
        return "jsp/owner/owner.jsp";
    }
}
