package by.bsuir.pbz2.service.dto;

import by.bsuir.pbz2.data.entity.enums.ExecutionType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExhibitionParticipantsAndArtworksDto {
    private String exhibitionName;
    private LocalDate exhibitionStartDate;
    private LocalDate exhibitionEndDate;
    private String artworkTitle;
    private ExecutionType executionType;
    private String artistName;
    private int artistAge;
    private LocalDate creationDate;
}
