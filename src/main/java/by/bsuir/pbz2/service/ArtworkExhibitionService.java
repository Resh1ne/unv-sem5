package by.bsuir.pbz2.service;

import by.bsuir.pbz2.service.dto.ArtworkDto;
import by.bsuir.pbz2.service.dto.ArtworkExhibitionDto;
import by.bsuir.pbz2.service.dto.ExhibitionDto;

import java.util.List;

public interface ArtworkExhibitionService {
    void create(ArtworkExhibitionDto dto);

    ArtworkExhibitionDto getByExhibitionArtworkId(ArtworkDto artworkId, ExhibitionDto exhibitionId);

    List<ArtworkExhibitionDto> getAll();

    void delete(Long id);
}
