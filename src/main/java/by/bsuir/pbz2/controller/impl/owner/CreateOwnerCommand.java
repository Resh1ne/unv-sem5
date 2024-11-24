package by.bsuir.pbz2.controller.impl.owner;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.data.entity.enums.OwnerType;
import by.bsuir.pbz2.service.OwnerService;
import by.bsuir.pbz2.service.dto.OwnerDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class CreateOwnerCommand implements Command {
    private final OwnerService ownerService;

    @Override
    public String execute(HttpServletRequest req) {
        log.info("Creating a new owner.");

        OwnerDto ownerDto = process(req);
        OwnerDto ownerDtoCreated = ownerService.create(ownerDto);

        log.info("Owner created with ID: {}", ownerDtoCreated.getId());
        req.setAttribute("owner", ownerDtoCreated);
        return "jsp/owner/owner.jsp";
    }

    private static OwnerDto process(HttpServletRequest req) {
        String name = req.getParameter("name");
        String type = req.getParameter("type");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");

        log.debug("Processing owner details: Name={}, Type={}, Address={}, Phone={}", name, type, address, phone);

        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setName(name);
        ownerDto.setOwnerType(OwnerType.valueOf(type));
        ownerDto.setAddress(address);
        ownerDto.setPhone(phone);
        return ownerDto;
    }
}
