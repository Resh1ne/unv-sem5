package by.bsuir.pbz2.data.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExhibitionHall {
    private Long id;
    private String name;
    private BigDecimal area;
    private String address;
    private String phone;
    private Owner ownerId;
}
