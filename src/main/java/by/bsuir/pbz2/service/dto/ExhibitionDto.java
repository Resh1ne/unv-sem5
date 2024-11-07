package by.bsuir.pbz2.service.dto;

import by.bsuir.pbz2.data.entity.ExhibitionHall;
import by.bsuir.pbz2.data.entity.enums.ExhibitionType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExhibitionDto {
    private Long id;
    private String name;
    private ExhibitionHall hallId;
    private ExhibitionType type;
    private LocalDate startDate;
    private LocalDate endDate;
}
