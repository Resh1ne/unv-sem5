package by.bsuir.pbz2.data.entity;

import by.bsuir.pbz2.data.entity.enums.ExecutionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class Artwork {
    private Long id;
    private String title;
    private ExecutionType executionType;
    private LocalDate creationDate;
    private BigDecimal height;
    private BigDecimal width;
    private BigDecimal volume;
    private Artist artistId;
}
