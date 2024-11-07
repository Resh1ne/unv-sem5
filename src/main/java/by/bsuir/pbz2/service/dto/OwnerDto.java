package by.bsuir.pbz2.service.dto;

import by.bsuir.pbz2.data.entity.enums.OwnerType;
import lombok.Data;

@Data
public class OwnerDto {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private OwnerType ownerType;
}
