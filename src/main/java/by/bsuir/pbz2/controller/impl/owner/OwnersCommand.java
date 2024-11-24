package by.bsuir.pbz2.controller.impl.owner;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.service.OwnerService;
import by.bsuir.pbz2.service.dto.OwnerDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class OwnersCommand implements Command {
    private final OwnerService ownerService;

    @Override
    public String execute(HttpServletRequest req) {
        log.info("Fetching all owners");

        List<OwnerDto> owners = ownerService.getAll();

        if (owners.isEmpty()) {
            log.warn("No owners found");
        } else {
            log.info("Fetched {} owners", owners.size());
        }

        req.setAttribute("owners", owners);
        return "jsp/owner/owners.jsp";
    }
}
