package by.bsuir.pbz2.service.dto;

import by.bsuir.pbz2.data.entity.Owner;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExhibitionHallDto {
    private Long id;
    private String name;
    private BigDecimal area;
    private String address;
    private String phone;
    private Owner ownerId;
}
