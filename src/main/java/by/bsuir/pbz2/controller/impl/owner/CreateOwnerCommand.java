package by.bsuir.pbz2.controller.impl.owner;

import by.bsuir.pbz2.controller.Command;
import by.bsuir.pbz2.data.entity.enums.OwnerType;
import by.bsuir.pbz2.service.OwnerService;
import by.bsuir.pbz2.service.dto.OwnerDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateOwnerCommand implements Command {
    private final OwnerService ownerService;

    @Override
    public String execute(HttpServletRequest req) {
        OwnerDto ownerDto = process(req);
        OwnerDto ownerDtoCrated = ownerService.create(ownerDto);
        req.setAttribute("owner", ownerDtoCrated);
        return "jsp/owner/owner.jsp";
    }

    private static OwnerDto process(HttpServletRequest req) {
        String name = req.getParameter("name");
        String type = req.getParameter("type");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setName(name);
        ownerDto.setOwnerType(OwnerType.valueOf(type));
        ownerDto.setAddress(address);
        ownerDto.setPhone(phone);
        return ownerDto;
    }
}
