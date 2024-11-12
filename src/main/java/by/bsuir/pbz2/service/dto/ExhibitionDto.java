package by.bsuir.pbz2.service.dto;

import by.bsuir.pbz2.data.entity.enums.ExhibitionType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExhibitionDto {
    private Long id;
    private String name;
    private ExhibitionHallDto hallId;
    private ExhibitionType type;
    private LocalDate startDate;
    private LocalDate endDate;
}
