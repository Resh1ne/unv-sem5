package by.bsuir.pbz2.data.entity;

import by.bsuir.pbz2.data.entity.enums.OwnerType;
import lombok.Data;

@Data
public class Owner {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private OwnerType ownerType;
}
