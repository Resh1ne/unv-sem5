package by.bsuir.pbz2.service;

import by.bsuir.pbz2.data.entity.Artwork;
import by.bsuir.pbz2.data.entity.Exhibition;
import by.bsuir.pbz2.service.dto.ArtworkExhibitionDto;

import java.util.List;

public interface ArtworkExhibitionService {
    void create(ArtworkExhibitionDto dto);

    ArtworkExhibitionDto getByExhibitionArtworkId(Artwork artworkId, Exhibition exhibitionId);

    List<ArtworkExhibitionDto> getAll();

    void delete(ArtworkExhibitionDto artworkExhibitionDto);
}
