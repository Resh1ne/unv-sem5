package by.bsuir.pbz2.service.impl;

import by.bsuir.pbz2.data.dao.ArtworkExhibitionDao;
import by.bsuir.pbz2.data.entity.Artwork;
import by.bsuir.pbz2.data.entity.ArtworkExhibition;
import by.bsuir.pbz2.data.entity.Exhibition;
import by.bsuir.pbz2.service.ArtworkExhibitionService;
import by.bsuir.pbz2.service.dto.ArtworkExhibitionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class ArtworkExhibitionServiceImpl implements ArtworkExhibitionService {
    private final ArtworkExhibitionDao artworkExhibitionDao;

    @Override
    public void create(ArtworkExhibitionDto dto) {
        log.info("Creating artwork-exhibition relation between artwork ID: {} and exhibition ID: {}", dto.getArtworkId(), dto.getExhibitionId());
        ArtworkExhibition artworkExhibition = toArtworkExhibitionEntity(dto);
        artworkExhibitionDao.create(artworkExhibition);
        log.info("Created artwork-exhibition relation with artwork ID: {} and exhibition ID: {}", dto.getArtworkId(), dto.getExhibitionId());
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
        log.info("Fetching artwork-exhibition relation for artwork ID: {} and exhibition ID: {}", artworkId.getId(), exhibitionId.getId());
        ArtworkExhibition artworkExhibition = artworkExhibitionDao.findByExhibitionArtworkId(artworkId, exhibitionId);
        if (artworkExhibition == null) {
            log.error("No artwork-exhibition relation found for artwork ID: {} and exhibition ID: {}", artworkId.getId(), exhibitionId.getId());
            throw new RuntimeException("No artwork-exhibition relation found");
        }
        log.info("Fetched artwork-exhibition relation for artwork ID: {} and exhibition ID: {}", artworkId.getId(), exhibitionId.getId());
        return toArtworkExhibitionDto(artworkExhibition);
    }

    @Override
    public List<ArtworkExhibitionDto> getAll() {
        log.info("Fetching all artwork-exhibition relations");
        List<ArtworkExhibitionDto> relations = artworkExhibitionDao.findAll()
                .stream()
                .map(this::toArtworkExhibitionDto)
                .toList();
        log.info("Fetched {} artwork-exhibition relations", relations.size());
        return relations;
    }

    @Override
    public void delete(ArtworkExhibitionDto artworkExhibitionDto) {
        log.info("Deleting artwork-exhibition relation for artwork ID: {} and exhibition ID: {}", artworkExhibitionDto.getArtworkId(), artworkExhibitionDto.getExhibitionId());
        ArtworkExhibition artworkExhibition = toArtworkExhibitionEntity(artworkExhibitionDto);
        artworkExhibitionDao.delete(artworkExhibition);
        log.info("Deleted artwork-exhibition relation for artwork ID: {} and exhibition ID: {}", artworkExhibitionDto.getArtworkId(), artworkExhibitionDto.getExhibitionId());
    }
}
