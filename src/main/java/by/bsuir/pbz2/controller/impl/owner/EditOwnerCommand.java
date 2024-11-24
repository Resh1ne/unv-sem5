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
public class EditOwnerCommand implements Command {
    private final OwnerService ownerService;

    @Override
    public String execute(HttpServletRequest req) {
        OwnerDto ownerDto = process(req);

        log.info("Updating owner with ID: {}", ownerDto.getId());

        OwnerDto ownerDtoUpdated = ownerService.update(ownerDto);

        log.info("Owner with ID: {} updated successfully", ownerDto.getId());

        req.setAttribute("owner", ownerDtoUpdated);
        return "jsp/owner/owner.jsp";
    }

    private static OwnerDto process(HttpServletRequest req) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String type = req.getParameter("type");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(Long.parseLong(id));
        ownerDto.setName(name);
        ownerDto.setOwnerType(OwnerType.valueOf(type));
        ownerDto.setAddress(address);
        ownerDto.setPhone(phone);
        return ownerDto;
    }
}
