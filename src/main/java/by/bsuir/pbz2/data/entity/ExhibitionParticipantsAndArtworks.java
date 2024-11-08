package by.bsuir.pbz2.data.entity;

import by.bsuir.pbz2.data.entity.enums.ExecutionType;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ExhibitionParticipantsAndArtworks {
    private String exhibitionName;
    private LocalDate exhibitionStartDate;
    private LocalDate exhibitionEndDate;
    private String artworkTitle;
    private ExecutionType executionType;
    private String artistName;
    private int artistAge;
    private LocalDate creationDate;
}
