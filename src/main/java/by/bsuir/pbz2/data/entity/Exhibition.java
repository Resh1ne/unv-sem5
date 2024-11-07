package by.bsuir.pbz2.data.entity;

import by.bsuir.pbz2.data.entity.enums.ExhibitionType;
import lombok.Data;

import java.time.LocalDate;


@Data
public class Exhibition {
    private Long id;
    private String name;
    private ExhibitionHall hallId;
    private ExhibitionType type;
    private LocalDate startDate;
    private LocalDate endDate;
}
