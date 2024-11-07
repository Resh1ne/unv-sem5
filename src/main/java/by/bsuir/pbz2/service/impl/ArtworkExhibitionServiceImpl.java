package by.bsuir.pbz2.service.impl;

import by.bsuir.pbz2.data.ArtworkExhibitionDao;
import by.bsuir.pbz2.data.entity.Artwork;
import by.bsuir.pbz2.data.entity.ArtworkExhibition;
import by.bsuir.pbz2.data.entity.Exhibition;
import by.bsuir.pbz2.service.ArtworkExhibitionService;
import by.bsuir.pbz2.service.dto.ArtworkExhibitionDto;

import java.util.List;

public class ArtworkExhibitionServiceImpl implements ArtworkExhibitionService {
    private final ArtworkExhibitionDao artworkExhibitionDao;

    public ArtworkExhibitionServiceImpl(ArtworkExhibitionDao artworkExhibitionDao) {
        this.artworkExhibitionDao = artworkExhibitionDao;
    }

    @Override
    public void create(ArtworkExhibitionDto dto) {
        ArtworkExhibition artworkExhibition = toArtworkExhibitionEntity(dto);
        artworkExhibitionDao.create(artworkExhibition);
    }

    private ArtworkExhibition toArtworkExhibitionEntity(ArtworkExhibitionDto dto) {
        ArtworkExhibition artworkExhibition = new ArtworkExhibition();
        artworkExhibition.setExhibitionId(dto.getExhibitionId());
        artworkExhibition.setArtworkId(dto.getArtworkId());
        return artworkExhibition;
    }

    private ArtworkExhibitionDto toArtworkExhibitionDto(ArtworkExhibition entity) {
        ArtworkExhibitionDto artworkExhibitionDto = new ArtworkExhibitionDto();
        artworkExhibitionDto.setExhibitionId(entity.getExhibitionId());
        artworkExhibitionDto.setArtworkId(entity.getArtworkId());
        return artworkExhibitionDto;
    }

    @Override
    public ArtworkExhibitionDto getByExhibitionArtworkId(Artwork artworkId, Exhibition exhibitionId) {
        ArtworkExhibition artworkExhibition = artworkExhibitionDao.findByExhibitionArtworkId(artworkId, exhibitionId);
        if (artworkExhibition == null) {
            throw new RuntimeException("No artwork-exhibition");
        }
        return toArtworkExhibitionDto(artworkExhibition);
    }

    @Override
    public List<ArtworkExhibitionDto> getAll() {
        return artworkExhibitionDao.findAll()
                .stream()
                .map(this::toArtworkExhibitionDto)
                .toList();
    }

    @Override
    public void delete(ArtworkExhibitionDto artworkExhibitionDto) {
        ArtworkExhibition artworkExhibition = toArtworkExhibitionEntity(artworkExhibitionDto);
        artworkExhibitionDao.delete(artworkExhibition);
    }
}
