package by.bsuir.pbz2.service.dto;

import by.bsuir.pbz2.data.entity.enums.ExecutionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ArtworkDto {
    private Long id;
    private String title;
    private ExecutionType executionType;
    private LocalDate creationDate;
    private BigDecimal height;
    private BigDecimal width;
    private BigDecimal volume;
    private ArtistDto artistId;
}
