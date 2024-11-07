package by.bsuir.pbz2.service.dto;

import by.bsuir.pbz2.data.entity.Artwork;
import by.bsuir.pbz2.data.entity.Exhibition;
import lombok.Data;

@Data
public class ArtworkExhibitionDto {
    private Exhibition exhibitionId;
    private Artwork artworkId;
}
